
# Cart Application

The Cart application is developed as part of the **Trendyol Developer Hiring** process. It is a simple **Spring Boot** application that implements the basic logic of a shopping cart. The application is designed to handle scenarios such as adding items to the cart, removing items, increasing quantities, and applying promotions. For more detailed information about the logic and implementation, please refer to the **[document](https://github.com/kahridev/cart/blob/main/README-2.md)**.

---

## **Swagger Usage**

Once the application is successfully running, you can access the Swagger interface to test API:  
`http://localhost:8080/swagger-ui.html`

### **Available Endpoint in Swagger**  
- **POST /cart/execute**  
  Use this endpoint to send the required `command` and `payload` to perform cart operations and view the `response` values. For details, please check the **[document](https://github.com/kahridev/cart/blob/main/README-2.md)**.
---

## **Building and Running the Application with Docker**

You can easily build and run the application using **Docker** by following the steps below:

### **1. Build the Docker Image**
To build the Docker image, execute the following command:
```bash
docker build --pull --rm -f "Dockerfile" -t cart:latest "."
```
This command will use the existing Dockerfile to build the application and create an executable Docker image.

### **2. Run the Application with Docker**
After building the Docker image, you can run the application using the command below:
```bash
docker run -p 8080:8080 cart
```
This command will start the application on port 8080.

---

## **Testing with Preloaded Test Data**

The application includes a set of preloaded test data to simplify testing. To test the application with the provided data, you can specify the JSON file path using the following parameter:
```bash
-DrequestFilePath=src/main/resources/requests/test-case-1.json
```
This parameter allows you to load and execute the commands from the specified JSON file.

Enjoy exploring the Cart Application!
