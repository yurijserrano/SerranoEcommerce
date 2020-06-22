![Java-Logo](https://i.imgur.com/ZjxiFYv.png)

# Serrano Ecommerce

> Serrano Ecommerce is the latest Java Developer Nanodegree project by Udacity

![Build Passing](https://img.shields.io/badge/build-passing-brightgreen) ![Code Coverage](https://img.shields.io/badge/coverage-100%25-brightgreen) ![Dependencies](https://img.shields.io/badge/dependencies-up%20to%20date-brightgreen) ![Jenkins CI/CD](https://img.shields.io/badge/jenkins-CI%2FCD-critical) ![Docker Version](https://img.shields.io/badge/docker-19.03.11-blue) ![Postman Test](https://img.shields.io/badge/postman-test-orange) ![Splunk](https://img.shields.io/badge/splunk-log-%23FF1493)  [![LICENSE](https://img.shields.io/badge/license-MIT-blue)](https://github.com/yurijserrano/SerranoEcommerce/blob/master/LICENSE.md)

## Table of Contents

- [Description](#description)
- [Getting Started](#getting-started)
- [AWS](#aws)
- [Jenkins](#jenkins)
- [Code Coverage](#code-coverage)
- [Docker](#docker)
- [Postman](#postman)
- [Splunk](#splunk)
- [Dependencies](#dependencies)
- [License](#license)


## Description

The Serrano Ecommerce is an application that aims to work on concepts such as DevOps, security, and analysis of logs, this is done through the implementation of a REST API, which simulates the process of purchasing of a user in an Ecommerce, in this case, the user performs the process of authentication and authorization, after that he/she can add items to your cart and eventually finish the purchase.

Regarding DevOps, the whole process of CI/CD was done by **[Jenkins](https://www.jenkins.io/)** which is available in an EC2 instance of **[AWS](https://aws.amazon.com/)** , having been installed through the **[Docker](https://www.docker.com/get-started)** for better performance and savings of the virtual machine resources.

For the security part, I used the **[Spring Security](https://spring.io/projects/spring-security)** and the **[JWT](https://auth0.com/docs/jwt)** to manage the authentication and authorization of the users.

For logging and analysis of logs I used the **[Splunk](https://www.splunk.com/)**, because it is very practical and effective for this type of task. 


## Getting Started

To test the application you just need to download the project and then run it in the IDE you find most suitable.

I recommend using the **[IntelliJ](https://www.jetbrains.com/idea/)**, because it is a very robust IDE that presents a great performance and has many features.

If you use **[IntelliJ](https://www.jetbrains.com/idea/)** you can run the application inside it or use the `mvn spring-boot:run` command in the terminal embedded in it to also run the application.

## AWS
> Here you can see all the setup I've done on AWS ☁️

<img src="https://i.imgur.com/d8XX4QO.png" width="45%"></img> <img src="https://i.imgur.com/hCOfCL0.png" width="45%"></img> <img src="https://i.imgur.com/OL6kFD8.png" width="45%"></img> <img src="https://i.imgur.com/8wSnNeJ.png" width="45%"></img> <img src="https://i.imgur.com/AKVPC7Q.png" width="45%"></img> <img src="https://i.imgur.com/x4qQILL.png" width="45%"></img> <img src="https://i.imgur.com/YVDUeQU.png" width="45%"></img> <img src="https://i.imgur.com/d21hshG.png" width="45%"></img> <img src="https://i.imgur.com/2kXUbRO.png" width="45%"></img> <img src="https://i.imgur.com/afGnjq1.png" width="45%"></img>

## Jenkins
> Here you can see all the setup I've done on Jenkins 🧐

![Jenkins CI/CD](https://img.shields.io/badge/jenkins-CI%2FCD-critical)

<img src="https://i.imgur.com/oNYuj3F.png" width="45%"></img> <img src="https://i.imgur.com/3WrFYzQ.png" width="45%"></img> <img src="https://i.imgur.com/frwJ9wK.png" width="45%"></img> <img src="https://i.imgur.com/pyzjepJ.png" width="45%"></img> <img src="https://i.imgur.com/r2mTpxo.png" width="45%"></img> <img src="https://i.imgur.com/K8twi56.png" width="45%"></img> <img src="https://i.imgur.com/9Z0ym2Z.png" width="45%"></img> <img src="https://i.imgur.com/up42jt2.png" width="45%"></img> <img src="https://i.imgur.com/MPs2a6r.png" width="45%"></img> <img src="https://i.imgur.com/IFO3OYI.png" width="45%"></img> <img src="https://i.imgur.com/Vft3YfJ.png" width="45%"></img> <img src="https://i.imgur.com/yhdHcsU.png" width="45%"></img>

## Code Coverage
> Here you can see the code coverage rate that my tests reached which by the way was 💯

![Build Passing](https://img.shields.io/badge/build-passing-brightgreen) ![Code Coverage](https://img.shields.io/badge/coverage-100%25-brightgreen)

<img src="https://i.imgur.com/R7Z9YK1.png" width="45%"></img> <img src="https://i.imgur.com/bWaDD0m.png" width="45%"></img> <img src="https://i.imgur.com/pJAeVOd.png" width="45%"></img> <img src="https://i.imgur.com/MSSK0hk.png" width="45%"></img>

## Docker
> Here you can see the build and running process of the image created by the docker 🐋

![Docker Version](https://img.shields.io/badge/docker-19.03.11-blue)

<img src="https://i.imgur.com/Q9fZu22.png" width="90%"></img> <img src="https://i.imgur.com/t5BhCZM.png" width="90%"></img> <img src="https://i.imgur.com/Z8IVO6z.png" width="90%"></img> <img src="https://i.imgur.com/FXbYwy7.png" width="90%"></img> <img src="https://i.imgur.com/YY1uQwZ.png" width="90%"></img>

## Postman
> Here you will be able to see all the test were used to verify all the endpoints of the application covering all the cases 💉

> Inside the `resources` folder, you will be able to find the `Postman Collection` that I created to make the test process faster. You just need to import inside the `Postman` to use

![Postman Test](https://img.shields.io/badge/postman-test-orange)


<img src="https://i.imgur.com/DaS2EZM.png" width="45%"></img> <img src="https://i.imgur.com/quxY8PR.png" width="45%"></img> <img src="https://i.imgur.com/QKNGfo5.png" width="45%"></img> <img src="https://i.imgur.com/wvXJeLT.png" width="45%"></img> <img src="https://i.imgur.com/G0akrfS.png" width="45%"></img> <img src="https://i.imgur.com/mgN9pDh.png" width="45%"></img> <img src="https://i.imgur.com/LBUNQEC.png" width="45%"></img> <img src="https://i.imgur.com/p6yHT1y.png" width="45%"></img> <img src="https://i.imgur.com/P5NcHCR.png" width="45%"></img> <img src="https://i.imgur.com/K93LQVH.png" width="45%"></img> <img src="https://i.imgur.com/MfeB3I3.png" width="45%"></img> <img src="https://i.imgur.com/KmY682U.png" width="45%"></img>
<img src="https://i.imgur.com/zYRhpBt.png" width="45%"></img> <img src="https://i.imgur.com/FJFUMZk.png" width="45%"></img> <img src="https://i.imgur.com/hWwkjfE.png" width="45%"></img>
<img src="https://i.imgur.com/Nj90LYO.png" width="45%"></img> <img src="https://i.imgur.com/SeXPCgw.png" width="45%"></img> <img src="https://i.imgur.com/TqP1jKR.png" width="45%"></img>
<img src="https://i.imgur.com/vs8NLD9.png" width="45%"></img> <img src="https://i.imgur.com/d7vPfV8.png" width="45%"></img> <img src="https://i.imgur.com/wd2Z5qi.png" width="45%"></img>
<img src="https://i.imgur.com/EQWtpDT.png" width="45%"></img> <img src="https://i.imgur.com/zmQGbCH.png" width="45%"></img>


## Splunk

> Here you will be able to see the configurations and features of Splunk

![Splunk](https://img.shields.io/badge/splunk-log-%23FF1493)

<img src="https://i.imgur.com/ttBtNUs.png" width="90%" height="60%"></img> <img src="https://i.imgur.com/gPrjpdD.png" width="45%"></img> <img src="https://i.imgur.com/D6U2u53.png" width="45%"></img> <img src="https://i.imgur.com/lNpE8IJ.png" width="45%"></img> <img src="https://i.imgur.com/RAXcPV5.png" width="45%"></img> <img src="https://i.imgur.com/1oRWooA.png" width="45%"></img> <img src="https://i.imgur.com/vhrZ07i.png" width="45%"></img> <img src="https://i.imgur.com/ler2D96.png" width="45%"></img> <img src="https://i.imgur.com/mc1BUmj.png" width="45%"></img> <img src="https://i.imgur.com/JmNZaFH.png" width="45%"></img> <img src="https://i.imgur.com/o9Cqhkk.png" width="45%"></img> <img src="https://i.imgur.com/p3PqRf5.png" width="45%"></img> <img src="https://i.imgur.com/UqB1ibF.png" width="45%"></img> <img src="https://i.imgur.com/Heo0sKq.png" width="45%"></img> <img src="https://i.imgur.com/eGibB2S.png" width="45%"></img> <img src="https://i.imgur.com/nZ5KPEx.png" width="45%"></img> <img src="https://i.imgur.com/C8Hj7H6.png" width="45%"></img> <img src="https://i.imgur.com/pGI2ENF.png" width="45%"></img> <img src="https://i.imgur.com/txCVuxB.png" width="45%"></img> <img src="https://i.imgur.com/LfAQn0d.png" width="45%"></img> <img src="https://i.imgur.com/LmCYnPk.png" width="45%"></img> <img src="https://i.imgur.com/cVuTrlW.png" width="45%"></img> <img src="https://i.imgur.com/5otyJMd.png" width="45%"></img>


## Dependencies

> Here you will find all the dependencies that I've used to build this project

![Dependencies](https://img.shields.io/badge/dependencies-up%20to%20date-brightgreen)

* [H2](https://www.h2database.com/html/main.html)
* [JaCoCo](https://github.com/jacoco/jacoco)
* [JWT](https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/)
* [Lombok](https://projectlombok.org/)
* [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
* [Spring Security](https://spring.io/projects/spring-security)
* [Spring Test](https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html)
* [Splunk](https://www.splunk.com/)


## License

[![LICENSE](https://img.shields.io/badge/license-MIT-blue)](https://github.com/yurijserrano/SerranoEcommerce/blob/master/LICENSE.md)

The related rights, as well as their rules and regulations for using this project, can be found **[HERE](https://github.com/yurijserrano/SerranoEcommerce/blob/master/LICENSE.md)**