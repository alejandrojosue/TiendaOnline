# TiendaOnline
Aplicación móvil sobre una tienda online (tomando como base una tienda de muebles).

### LENGUAJES
- NodeJS (```v18.15.0```)
- Kotlin

### PLATAFORMA
- Android Studio    =>(frontend)
- Strapi (```v4.10.1```) =>(backend)
- npm (```v9.5.0```)

### INSTALACIÓN

1. Importar la base de datos MYSQL ubicada en la carpeta database llamada ```tiendaOnline```
2. En la carpeta ```backendStrapi``` en una terminal.
3. Ejecutar comando ```npm install``` para instalar dependencias de nodejs
4. Luego ingresar en la consola ```yarn build```
5. En la carpeta ```backendStrapi``` crear un archivo llamado *.env* y escribir las siguientes variables:
  ~~~
  HOST=0.0.0.0
PORT=1337
APP_KEYS=5KizE2tZWZQ0WsO4C2BIjw==,/rcK8+i7XcROovKVT/4Ejg==,3tbYc4NGLpX2sS2UToYwfQ==,nXeKK3+1IwHfAm/l8O55YQ==
API_TOKEN_SALT=S/zoxGwDir7Kb4I+HALySw==
ADMIN_JWT_SECRET=ijyauY029TEvzfnKILg5Fg==
TRANSFER_TOKEN_SALT=Be5y82EN/8J/hhpXOQvVZQ==
# Database
DATABASE_CLIENT=mysql
DATABASE_HOST=127.0.0.1
#db4free.net
DATABASE_PORT=3306
DATABASE_NAME=tiendamuebles
DATABASE_USERNAME=
DATABASE_PASSWORD=

DATABASE_SSL=false
JWT_SECRET=8f+Z6PvTwskBsjav4SSa2A==

#TREBLLE_API_KEY=jHmqwBNHUAAWkwrn3M46i3bevbttCqFL
#TREBLLE_PROJECT_ID=IAvlrhtAiB9h56WT
SENDGRID_API_KEY=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
GMAIL_KEY=biyvxblpoqqutson
~~~

y cambiar el usuario y contraseña

6. Al finalizar instalación, ejecutar el comando ```yarn develop```
