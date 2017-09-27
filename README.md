# jtshirt-demo

The program is not finished yet.

## Install
It's a Spring Boot app with Maven so it should be straightforward to make it work.  
Be sure to look at application.properties to configure the app: server port, folder for the t-shirts images, database access...  

Check *postman-collections* folder to have examples of the API calls.
Basically, we have:  
  - /api/tshirts  ==> Get all the t-shirts  
  - /api/tshirts/{id}  ==> Get the t-shirt #{id}
  - /api/orders/{id}  ==> Get the order #{id} 
  - /api/orders/place  ==> Place an order (check Postman collection for the JSON structure) 

**Requires Java 8.** 

## Done
T-shirt CRUD  
Image support  
API for t-shirts listing  
Tests for t-shirts API  
API for order  
Backend auth with Spring security  
Tests for order API  

## TODO  
Tests for t-shirts portal (maybe)

## Won't be done
Complete i18n with messages.properties. Currently, I use it only for custom error messages.