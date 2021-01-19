# ShoppingAppMVVMUnitTest

Project focused on Unit Test in MVVM pattern in TDD implementation practice

## Information

Use the API from pixabay (https://pixabay.com/api/docs/) to request data.</br></br>
The proyect use Kakao DSL Unit Test (https://github.com/agoda-com/Kakao) read to info.</br></br>
Kakao test article (https://www.raywenderlich.com/1505688-ui-testing-with-kakao-tutorial-for-android-getting-started) read to info.</br></br>


## Usage

 - Import project or download it via zip or Git Client
 - Open project with Android Studio and check gradle to version 4.0+
 - Verify updated Android SDK AndroidX is already used throughout the project

The project uses the MVVM + Repository design pattern, so prior knowledge of the pattern is recommended to understand the complexity of the development, in addition to the use of Koin as a dependency injector.

Generate your API_KEY in the site Pixabay api

Build proyect to create the archive gradle.properties and add your ApiKey from Pixabay

```gradle.properties
API_KEY_PIXA_BAY = "YOUR_API_KEY"
```
this proyect is enfoqued in unit test in Android witch the use the MVVM repository pattern, test in fragment, test DB witch ROOM, and navigation component.

## Technologies or librarys

Application
- Glide
- Retrofit
- Coroutines
- Room
- Navigation Component
- Koin 
- Lifecycle
- Material Component

Test Dependencys
- Junit
- Hamcrest
- Roboelectric
- Coroutines Test
- Truth
- Mockito
- Expresso
- Koin test
- Kakao



## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.


## License
[MIT](https://choosealicense.com/licenses/mit/)
