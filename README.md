# Проектна задача по информациска безбедност
## Е-систем за издавање лекови со автентикација со X.509 сертификати и контрола на пристап 
### Андреј Даскалов 201125
---

# Функционалности
##  Кога е најавен **ADMIN**:
- Mенаџирање на лекови
![](https://gcdnb.pbrd.co/images/gk3MjCvB0mYr.png?o=1)
- Додавање лекови
![](https://gcdnb.pbrd.co/images/fXhY6pAdvUwI.png?o=1)
- Менањирање со производители
![](https://gcdnb.pbrd.co/images/4yIdquiPkOJ7.png?o=1)
- Додавање производител
![](https://gcdnb.pbrd.co/images/n8dXH36f1aNu.png?o=1)
- регистрирање на корисник
![](https://gcdnb.pbrd.co/images/i3dUBxTEMN1N.png?o=1)
## Кога е најавен **DOCTOR**:
- менаџирање со рецепти
![](https://gcdnb.pbrd.co/images/rR9QiKZcdxeN.png?o=1)
- додавање рецепта
![](https://gcdnb.pbrd.co/images/AX7QBETDAFch.png?o=1)
## Кога е најавен **PATIENT**:
- преглед на рецепти издадени нему
![](https://gcdnb.pbrd.co/images/62hVHTs3Luk8.png?o=1)
# Tech Stack
Проектот е изработен во **Spring Boot**, со **Spring Security 6** и **Spring Data JPA** со **PostgreSQL** датабаза. Корисничкиот интерфејс е изработен со **Thymeleaf** со додаден **Bootstrap 5**.

# За апликацијата
Изработена е со стандардна MVC слоевита архитектура. 
## Модели
- ```User``` : енкапсулира информации за корисниците: ЕМБГ (во улога на уникатно корисничко име), име, и тип на корисник (**Ова е од значење бидејќи вака се чуваат *roles*** ).
- ```UserType```: енумерација за roles (ADMIN, DOCTOR и PATIENT) 
- ```Manufacturer``` : производител на лекови
- ```Medicine``` : тип/специфичен модел на лек за кој може да се издаде како рецепта. 
- ```Prescription```: рецепта за даден лек на специфичен пациент. 
### Репозиториуми и Сервиси
Секој од моделите има соодветен JpaRepository интерфејс, како и сервисна логика потребна за CRUD операции. 

Од особено значење е **UserServiceImpl** кој не само што имплементира **UserService**, туку и **UserDetailsService**, кој е потребен за Security да може да презема информации за корисник од база.

### Контролери
| Контролер                    | Патекa                 | Контрола на пристап     |
|------------------------------|------------------------|-------------------------|
| ```MedicineController```     | ```/medicine/**```     | **ADMIN**               |
| ```ManufacturerController``` | ```/manufacturer/**``` | **ADMIN**               |
| ```PrescriptionController``` | ```/prescription/**``` | **DOCTOR**              |
| ```AccountController```      | ```/accounts/**```     | **ADMIN**               | 
| ```MainController```         | ```/```                | сите најавени корисници |

>```MainController``` ги прикажува рецептите на моментално најавениот пациент. Во случај да е најавен друг вид на корисник, го пренасочува на соодветна патека. 



# Взаемна автентикација меѓу сервер и клиент
## SSL конфигурација на сервер 
Користев **OpenSSL** за генерирање на сертификатите.
следуваат чекорите за генерирање на сертификати
1. генерирање на приватен клуч: ```openssl genrsa -des3 -out domain.key 2048```
2. креирање на CSR (Certificate Signing Request): ```openssl req -key domain.key -new -out domain.csr```
3. генерирање на само-потпишан(self-signed) сертификат: ```openssl x509 -signkey domain.key -in domain.csr -req -days 365 -out domain.crt```
4. креирање на само-потпишан Root CA>: ```openssl req -x509 -sha256 -days 1825 -newkey rsa:2048 -keyout rootCA.key -out rootCA.crt```
5. конфигурациска датотека за потпишување ```domain.ext``` со содржина:  

```authorityKeyIdentifier=keyid,issuer
basicConstraints=CA:FALSE
subjectAltName = @alt_names
[alt_names]
DNS.1 = domain
```
6. потпишување на CSR со ново-креираниот root CA: ```openssl x509 -req -CA rootCA.crt -CAkey rootCA.key -in domain.csr -out domain.crt -days 365 -CAcreateserial -extfile domain.ext```
7. конвертирање во PKCS12 формат за импортирање на серверот: ```openssl pkcs12 -inkey domain.key -in domain.crt -export -out keystore.p12```
8. импортирање на ```keystore.p12``` во Spring и соодветна конфигурација во ```application.properties``` : 
```
server.port=8443
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-type=PKCS12
server.ssl.key-store-password=daskalov
```
> бидејќи користиме само-потпишан root CA, **потребно е истиот да се импортира во browser** за да биде препознаен како валиден сертификат. Ова не е потребно како чекор доколку користевме сертификат потпишан од познат CA

## Конфигурација на клиентска автентикација со X.509 сертификат
за генерирање на клиентските сертификати, во проектот имам креирано bash скрипта која ги извршува конандите за генерирање и потпишување на CSR, како и негово експортирање во p12 формат. Скриптата како аргумент прима име на корисникот, кое ќе го користи како име на датотеките кои се генерираат. Содржината е следна:
```
#!/bin/bash

if [[ $# -eq 0 ]]; then
  echo "Usage: $0 <name>"
  exit 1
fi

name=$1

# Generate CSR
openssl req -new -newkey rsa:4096 -nodes -keyout "${name}.key" -out "${name}.csr"

# Sign CSR with root CA and create certificate
openssl x509 -req -CA rootCA.crt -CAkey rootCA.key -in "${name}.csr" -out "${name}.crt" -days 365 -CAcreateserial

# Create PKCS#12 file containing private key and certificate
openssl pkcs12 -export -out "${name}.p12" -name "${name}" -inkey "${name}.key" -in "${name}.crt"
```

Како CN (Common Name) во prompt на командите се пишува ЕМБГ на корисникот и **МОРА** да се совпаѓа со вредноста зачувана во база.

### Keystore
1. пакување на приватниот клуч на серверот со сертификатот во p12 фајл :
```openssl pkcs12 -export -in domain.crt -inkey privateKey.key -out keystore.p12```
2. конвертирање во ЈКS датотека: ```keytool -importkeystore -srckeystore keystore.p12 -srcstoretype PKCS12 -destkeystore keystore.jks -deststoretype JKS```
3. импортирање на keystore во ```resources``` и додавање во ```application.properties```: 
```
server.ssl.trust-store=classpath:truststore.jks
server.ssl.trust-store-password=daskalov
server.ssl.client-auth=need
```
### Spring Security конфигурација

```java
@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests(
                (auth) -> {
                    try {
                        auth
                                .requestMatchers("/medicine/**", "/manufacturer/**", "/account/**").hasRole("ADMIN")
                                .requestMatchers("/prescription/**").hasRole("DOCTOR")
                                .anyRequest().authenticated()
                                .and()
                                .x509()
                                .subjectPrincipalRegex("CN=(.*?)(?:,|$)")
                                .and()
                                .logout()
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID")
                                .invalidateHttpSession(true)
                                .logoutSuccessUrl("/");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return http.build();
    }
```
.
овде Spring Security е конфигурирано да презема x509 сертификат и да го извлече CN преку regex (имплементирано со филтер во позадина), како и конфигурирано да ограничува пристап според патеки:
- ```/medicine/**``` , ```/manufacturer/**``` и ```/accounts/**`` да се достапни само ако најавениот користник има ролја **ADMIN**
- ```/prescriptions/**``` се само достапни на корисници со ролја **DOCTOR**

Дополнително, сите корисници мора да се автентицирани за пристап, т.е. да имаат сертификат.

# Дополнителни безбедносни ограничувања
Бидејќи некои од погледите се споделени од различни контролери, со тоа и различни видови на корисници, некои елементи во самите Thymeleaf templates се ограничени да рендерират само при присуство на одреден role.


