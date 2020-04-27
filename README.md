# payment-microservice
Micro-service for processing credit card payments in web and mobile apps. REST API's are provided to
handle the most common credit card processing activities.

## Table of Contents
 * [Prerequisites](#prerequisites)
 * [Installation](#installation)
 * [Usage](#usage)
   * [Charge API](#charge)
   * [Customer API](#customer)
   * [Cards API](#cards)
   * [Tokenization (client-side)](#stripeToken)

## <a name="prerequisites"></a>Prerequisites
This is Spring Boot application built using Maven.  

[Stripe API](https://stripe.com) is used to handle credit card information. You will need a stripe public key and secret key
which you can get when you register for a [Stripe Account](https://stripe.com/docs/keys).

## <a name="installation"></a>Installation
1. Clone this repository (or download and extract the zip file)
2. Open a command prompt or terminal
3. Change to the directory that contains the project files
4. Change the values of STRIPE_PUBLIC_KEY and STRIPE_SECRET_KEY to your own keys in the application.properties file at src/main/resources
5. Run the code below to build a jar file and run it
``` 
  ./mvnw package  
  java -jar target/*.jar
```
You can access the service at http://localhost:8080/

## <a name="usage"></a>Usage
This service has three basic functionalities/API's;
* [Charge](#charge): which is used for making the payment/ charging the credit card provided with the amount given
* [Customer](#customer): which handles saving the card of a user so they don't have to input their details when they come back to use the service
* [Cards](#cards): which is used to retrieve a list of saved cards for a specific user/customer

### <a name="charge"></a>Charge API
#### Description
Charges selected credit card with given amount in given currency. 
#### Parameters
* stripeToken - Tokenized credit card from [Stripe Tokens API](https://stripe.com/docs/api/tokens). Should be done in client-side for security. (Demo [here](#stripeToken).)
* amount(required) - Transaction amount in number. (Decimal/Integer)
* currency(required) - Transaction currency ie USD
* name - Name of the customer/user.
* stripeCustomerId - the customer id returned by Customer API, should come from a database or other storage
* cardId - id of a card belonging to an existing customer/user obtained from Card API
#### Request
`POST /charge`
#### Body
```javascript
{
  stripeToken: token,
  amount: 9.99
  currency: 'USD',
  name: 'Random Name',
  //stripeCustomerId: 'cus_H7TyazUl91DKMD',
  //cardId: 'card_1GZF0zCZvnRsVDlfZpXbIoBw',
}
```
#### Return
```javascript
{
  status: 'succeeded',
  error: null,
  ... //other data omitted for brevity
}
```

### <a name="customer"></a>Customer API
#### Description
Saves credit card details for a customer/user and returns stripeCustomerId. 
If stripeCustomerId is given, then the credit card to save is added to the customer. 
If no stripeCustomerId, new customer is created and stripeCustomerId is returned.
#### Parameters
* stripeCustomerId - Customer id returned by Customer API, should come from a database or other storage
* stripeToken(required) - Tokenized credit card from [Stripe Tokens API](https://stripe.com/docs/api/tokens). Should be done in client-side for security. (Demo [here](#stripeToken).)
* name - Name of customer/user
* email - Email of customer/user
#### Request
`POST /customer`
#### Body
```javascript
{
  stripeCustomerId: 'cus_H7TyazUl91DKMD',
  stripeToken: token,
  name: 'Random Name',
  email: 'email@gmail.com',
}
```
#### Return
```javascript
{
  id: 'cus_H7TyazUl91DKMD', //stripeCustomerId
  ... //other data omitted for brevity
}
```

### <a name="cards"></a>Cards API
#### Description
Fetches the list of credit cards saved for a customer/user.
#### Parameters
* stripeCustomerId(required) - the customer id returned by Customer API, should come from a database or other storage
#### Request
`POST /cards`
#### Body
```javascript
{
  stripeCustomerId: 'cus_H7TyazUl91DKMD'
}
```
#### Return
```javascript
{
  data: [
    {
      brand: 'MasterCard',
      exp_month: 4,
      exp_year: 2024,
      id: 'card_1GZF0zCZvnRsVDlfZpXbIoBw', //cardId
      last4: '4444', //last 4 digits of the credit card number
      ... //other data omitted for brevity
    },
  ],
  ... //other data omitted for brevity
}
```

### <a name="stripeToken"></a>Tokenization
Tokenization of credit card example in javascript client. Returns token object to use in other API's.
```javascript
const STRIPE_PUBLIC_KEY = mystripePublicKey //replace
const card = {
        'card[number]': 4242424242424242, //test card
        'card[exp_month]': 04, //mm
        'card[exp_year]': 24, //yy
        'card[cvc]': 123,
        'card[name]': 'Random Name', //cardholder's name
    }
    
    fetch( 'https://api.stripe.com/v1/tokens', {
      method: 'POST',
      headers: {
        'Contet-Type': 'application/x-www-form-urlencoded',
        Authorization: `Bearer ${STRIPE_PUBLIC_KEY}`
      },
      body: Object.keys(card) 
                .map(key => key + '=' + card[key])
                .join('&')   
    })
  
```

