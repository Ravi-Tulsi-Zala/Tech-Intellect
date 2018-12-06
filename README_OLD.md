# Tech Intellect
## Team Members
#### Smit Saraiya (B00811636, sm252977@dal.ca) 
#### Ravi Zala (B00805073, rv901764@dal.ca) 
#### Yash Modi (B00799125, ymodi@dal.ca) 
#### Haritha Desikan (B00801722, haritha.desikan@dal.ca)
#### Aravind Govindarajan (B00803336, aravind.govindarajan@dal.ca)

The project is available at the following location:
[Tech Intellect](https://git.cs.dal.ca/modi/Tech-Intellect.git)

# Project Summary
Tech Intellect is a technical quiz application that tests the users on some interesting topics like Natural Language Processing, Computer Science Acronyms, R Programming, and Google go. The quiz application has three levels of difficulty namely easy, medium and hard for the users to select according to their expertise in that particular topic. The app delivers the quiz to users in two different modes namely Timed and Endless. Timed mode forces users to finish the quiz within a stipulated time depending on the difficulty level and Endless mode allows the users to take the quiz and learn on their own pace.

## Libraries
Following are the list of external libraries used for this project:

**Better Spinner** : Library for creating materialistic drop-down spinner. Source [here](https://github.com/Lesilva/BetterSpinner/blob/master/library-material/src/main/java/com/weiwangcn/betterspinner/library/material/MaterialBetterSpinner.java)

**Firebase database library**: This library helps to interact with firebase database . Data is synced across all clients in realtime, and remains available even when the app goes offline. Data is stored as JSON and synchronized in realtime to every connected client.

**Firebase auth library**: Library provides authentication of user with Firebase. When user sign into app, it authenticates user using credentials. These credentials can be the user's email address and password, or an OAuth token from a federated identity provider. Backend services of Firebase will then verify those credentials and return a response to the client. After a successful sign in, one can access the user's basic profile information, and control the user's access to data stored in other Firebase products.



## Installation Notes
Installation instructions for markers.

## Code Examples
While developing the project, we faced quite a few setbac. Butks as a team, we worked together to get the optimal solution for all the problems. 
 
***Problem 1***: We needed a method to check if the device is connected to internet Since our quiz applications requires network connection, we had to check if the user has an active internet connection while using the app. This was essential because our app kept crashing when there is no active connection.
```
//Method to check if there is active internet connection  
public boolean isConnectedToInternet(){  
    ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);  
    if (connectivity != null)  
    {  
        NetworkInfo[] info = connectivity.getAllNetworkInfo();  
        if (info != null)  
            for (int i = 0; i < info.length; i++)  
                if (info[i].getState() == NetworkInfo.State.CONNECTED)  
                {  
                    return true;  
                }  
  
    }  
    return false;  
}
```
***Problem 2***: We found it difficult to implement the dynamic allocation of card layout in dashboard. The problem we are facing currently is that the cards are being overwritten instead of being appended to the previous card.
```
public View onCreateView(LayoutInflater inflater, ViewGroup container,  
                         Bundle savedInstanceState) {  
    // Inflate the layout for this fragment  
  View root = inflater.inflate(R.layout.fragment_tab2, container, false);  
    RecyclerView recyclerView = (RecyclerView)root.findViewById(R.id.recyclerview);  
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));  
    
    //According to listsize cards will be formed dynamically 
    recyclerView.setAdapter(new RecyclerViewAdapter(getContext(),Tab1.flist)); 
    return root;  
  
}
```
***Problem 3***:  Dashboard disable functionality for guest using intent was quite difficult. 
```
private Button btnGuest;  

//Guest flag is false in the beginning
public static boolean signInGuestflag = false;
```
```
btnGuest.setOnClickListener(new View.OnClickListener() {  
    @Override  
  public void onClick(View v) {  
  
        Intent intent = new Intent(getApplicationContext(), HomeScreen.class);  
        signInGuestflag = true;  
        startActivity(intent);  
  
    }  
});
```
```
if(!MainActivity.signInGuestflag)  
{  
    tabLayout.addTab(tabLayout.newTab().setText("Dashboard"));  
    MainActivity.signInGuestflag=false;  
  
}
```

## Feature Section
The main features of the application are listed below.

**Registration**: Allows new users to either register with the quiz application by providing their name, email address, and password.

**Login**:  Allows existing users to log into the quiz application using their email address and password which was created at the time of registration.

**Guest login**:  Users who don't wish to register with the application, but would like to get a flavour of the app, can use this feature to login as a guest and take the quiz. 

**Difficulty levels**: Tests the users on the expertise level they have opted. The quiz application supports three level of difficulties, namely easy, medium and hard. 

**Timed mode**: Forces the users to finish the quiz within a stipulated time depending on the difficulty level.

**Endless mode**: Allows the users to take the quiz and learn at their own phase.

**Dashboard**: Enables the users to visit their past learning history. Once a quiz is successfully completed, the corresponding quiz will be created dynamically under the dashboard tab. On clicking it, users can go through the questions and answers  that was asked in the quiz. Note that this feature is not available for guest users.
 
 **Buzzer and vibration**: The app has buzzer sounds for wrong and right answers. This can be controlled under the settings menu. Also, the app vibrates for every wrong answer. Just like the buzzer sounds, vibration can also be turned on/off in the settings menu. 

 **Forgot password**: Helps users to reset their password. The app sends an email to the users' registered email address. The link provided in the email can be used to set the new password.
 
  **Change password**: Helps users to change their old password and set a new password. 


## Final Project Status
We have successfully implemented the major functionalities of our application. 

The next step of our project is to implement a 
repetition algorithm that would repeat the wrong questions during the quiz. This can help the users to rectify their mistakes and learn from it. We also look forward to add more questions for the topics provided. 

#### Minimum Functionality

A learning quiz application with login and registration module. The application will allow users to gain knowledge on interesting topics like computer science acronyms, natural language processing, Google’s Go and R programming language *[Completed].*

#### Expected Functionality

 1. **Guest login** feature for the users who don't wish to register with the application, but would like to get a flavour of the app **[Completed]**.
 
 3. The quiz application will have **three levels of difficulty** namely *easy, medium and hard* for the users to select according to their expertise in that particular topic **[Completed]**.
  3.  The application will deliver the quiz to users in **two different modes** namely *Timed and Endless. Timed mode will force users to finish the quiz within a stipulated time depending on the difficulty level and Endless mode will let users to take the quiz and learn on their own pace **[Completed]**.
  4. The **dashboard tab** on the home screen of the application will display the *user’s learning history. Guest users will not be able to use the dashboard feature [Completed 90%*].
  5. The application will alert the users with a *vibration* when a wrong answer has been submitted. This vibration feature can be configured by the user in the Settings menu **[Completed]**.
  6. The app will play **buzzer sounds** for right and wrong answers. The buzzer feature can be configured by the user in the Settings menu **[Completed]**.
 

#### Bonus Functionality

 1. Implementation of **change password** and **forgot password** functionalities for user convenience **[Completed]**.
 
 2. The application will repeat the wrongly answered questions to users by using a repetition algorithm. In this way, users can learn from their mistakes easily **[Not implemented]**.

## Sources

[1]"Login and registration with user details using firebase in Android studio | 2017 latest", YouTube, 2018. [Online]. Available: https://www.youtube.com/watch?v=VFS-wfz9Nb4&t=859s. 

[2]T. San, "Android Firebase: User Registration and Login with email and password", YouTube, 2018. [Online]. Available: https://www.youtube.com/watch?v=xt9elnnUGRw&t=1279s.