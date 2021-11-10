## Dolphin Chat Android Library Documentation

## Version

- 1.0.0

# Getting Started

## Step 1: Get Your Credential

First, you need to get a **websocket server url, client id and client secret**. you can get it on the 3Dolphin channel dashboard.

## Step 2: Install 3Dolphin Live Chat SDK

Current 3Dolphin Live Chat SDK requires **minimum Android API 19 (KitKat)**. To integrate your app with 3Dolphin SDK, it can be done in 2 (two) steps.

1. You need to import our library to your project.
2. You need to add SDK dependencies inside your app .gradle. Then, you need to synchronize to compile the 3Dolphin SDK for your app.

```java
dependencies {
		...
		implementation project(path: ':dolphinlib')
}
```

3.  You need to add this code to .gradle module project

```java
allprojects {
    repositories {
				...
        mavenCentral()
        maven { url "https://jitpack.io" }
        
    }
}
```

## Step 3: Initialization 3Dolphin Live Chat SDK

You need to initiate your **server** **url, client id and client secret** for your chat app before carry out to authentication. This initialization only needs to be done once in the app lifecycle. Initialization can be implemented in the initial startup. Here is how you can do that:

```java
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DolphinCore.init(
                this,
                CLIENT_ID,
                CLIENT_SECRET,
                BASE_URL_SERVER,
                BASE_URL_SERVER_WEBSOCKET
        );
    }
}
```

> The initialization should be called once across an Android app. The best practice you can put in application class.

## Step 4: Connect to Server

First, you must initialize the Dolphin Connector. Here is how you can do that:

```java
DolphinConnect.getInstance().init(dolphinProfile);
```

where:

- DolphinProfile is the identity of the user who will chat. dolphinProfile contain:
    - username (String): name of the user
    - email(String): user email
    - phoneNumber(String): user phone number
    - customerId(String)
    - uid(String)
- If you have additional field that will send to server, you can put it in customVariables
    - customVariables (JsonArray). example:

    ```java
    JSONArray customVariables = new JSONArray();
    JSONObject field = new JSONObject();
    field.put("name", "Doni");
    customVariables.put(field);

    dolphinProfile.setCustomVariables(customVariables);
    ```

After dolphin connector is successfully initiated. You can authenticate to the server by:

```java
try {
    DolphinConnect.getInstance().connect();
} catch (Exception e) {
    e.printStackTrace();
}
```

# Event Handler

Dolphin Chat SDK is using EventBus for broadcasting events to the entire applications. What you need to do is register the object which will receive event from EventBus. You can call it like this:

```java
EventBus.getDefault().register(this);
```

> You can put this code in onResume() or onStart() method

Once you don't need to listen to the event anymore, you have to unregister the receiver by calling this method:

```java
EventBus.getDefault().unregister(this);
```

> You can put this code in onDestroy() method

# Receiving Event

## Connection Event

To find out the connection status to the server, you can do it by:

```java
@Subscribe
public void connectionEvent(DolphinConnectEvent event) {
	// event.getStatus();      get connection status
	// event.getMessage();     get connection message
}
```

## Get Chat History

Dolphin Chat SDK allows you to turn on/off the get chat history feature. you can set it by:

```java
DolphinCore.setIsEnableHistory(true);
```

> Default value of this property is **true**

To get chat history results, you can do it by:

```java
@Subscribe
public void getChatHistory(DolphinChatHistoryEvent event) {
    if (event.getStatus().equals(DolphinChatHistoryEvent.Status.SUCCESS)) {
		// Do Something
		// event.getStatus();         get status
		// event.getDolphinChats();   get chat data
    } else {
        // Do Something
		// event.getMessage();        get message status
    }
}
```

## Receiving Message

To receive messages, you can do this by:

```java
@Subscribe
public void receiveMessage(DolphinChatEvent event) {
        switch (event.getStatus()){
            case CHAT:
                // Do Something
				// event.getDolphinChat();     get chat data
				// event.getType();            get chat type
                break;
            case MESSAGE_READ:
				// Do Something
				// event.getChatId();          get chat id
				// event.getType();            get chat type
                break;
            case MESSAGE_SENT:
				// Do Something
				// event.getChatId();          get chat id
				// event.getType();            get chat type
                break;
            case MESSAGE_SEND_FAILED:
				// Do Something
				// event.getStatus();          get event status
                break;
        }
    }
```

## Send Message

```java
DolphinConnect.getInstance().sendMessage("text message");
```

## Send Media Message

```java
DolphinConnect.getInstance().sendFile(file, DolphinChat.Type.IMAGE);
```

> There are 4 media type, that is: IMAGE, DOCUMENT, AUDIO and VIDEO

## Trigger Menu Configuration

trigger menu is used to trigger the connector to send the first message to the server after the user has successfully logged in. The first message to be sent can be set in the following way:

```java
DolphinCore.setTriggerMenu("menu");
DolphinCore.setShowTriggerMenuMessage(true);
```

Where:

- showTriggerMenuMessage is used to display or not messages that have been set on triggerMenudisplay