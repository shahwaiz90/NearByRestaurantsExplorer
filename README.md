# NearByRestaurantsExplorer 
An app to explore near by restaurants using foursquare API. Navigation is also implemented with animation.

# Demo:

Click on the image to see demo on YouTube.

![Watch the Demo](https://img.youtube.com/vi/oZjw3lOB6OM/2.jpg)


Below is the detail of the architecture, libraries & security used in the project. 

 - **Clean Architecture**
     - **Presentation Layer**
        - View + View Model
     - **Domain Layer**
        - MapsUseCase
     - **Data Layer** 
        - MapsRepository
        - ApiService 
        
## Architecture
![Architecture](https://uploads.toptal.io/blog/image/127608/toptal-blog-image-1543413671794-80993a19fea97477524763c908b50a7a.png)

## Flow of API Call
Activity/Fragment -> ViewModel -> MapsUseCase -> MapsRepository -> ApiService

### JetPack libraries being used in our project

 - [Hilt Library](https://developer.android.com/training/dependency-injection/hilt-jetpack)
    - Hilt is a new official dependency injection(DI) library for Android. It provides an organized way to use DI in your Android app by providing containers for every Android class in your project and managing their lifecycles automatically.
    - Every Module has its own Module suffix class file to generate dependencies needed by other or its own module files.

 - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    - The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.
 - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
    - LiveData is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services. This awareness ensures LiveData only updates app component observers that are in an active lifecycle state.
 - [Navigational Components](https://developer.android.com/jetpack/androidx/releases/navigation)
    - Navigation is a framework for navigating between 'destinations' within an Android application that provides a consistent API whether destinations are implemented as Fragments, Activities, or other components.
 - [Security (security-crypto)](https://developer.android.com/jetpack/androidx/releases/security)
    - Safely manage keys and encrypt files and sharedpreferences.
 - [AppCompat](https://developer.android.com/jetpack/androidx/releases/appcompat)
    - Allows access to new APIs on older API versions of the platform (many using Material Design).
 - [Testing](https://developer.android.com/jetpack/androidx/releases/test)
    -  Major test libraries to test android
 - [Fragment](https://developer.android.com/jetpack/androidx/releases/fragment)
    - Segment your app into multiple, independent screens that are hosted within an Activity.
    

