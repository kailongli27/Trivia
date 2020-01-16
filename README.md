# Trivia
A true or false trivia android application built with Java. The app has the following special features:

- Uses a free JSON API to obtain a total of 913 trivia questions, as implemented in the [QuestionBank](app/src/main/java/com/example/trivia/data/QuestionBank.java) class.
- If the user answers correctly, a fade animation is executed and the background colour temporarily switches to green.
- If the user answers incorrectly, a [shaking](app/src/main/res/anim/shake.xml) animation is executed and the background colour temporarily switches to red.
- Once the user submits an answer, the application automatically moves on to the next question.
- The user can return to the previous question or go to the next question using the previous and next buttons.
- The application keeps track of the user's current score and highest score. The user is granted 100 points for a correct answer and deducted 100 points for a wrong answer.
- The application saves the user's highest score after the user exists the app using Shared Preferences, as implemented in the [Prefs](app/src/main/java/com/example/trivia/util/Prefs.java) class.

### Important Files

- [Layout File for this Application](app/src/main/res/layout/activity_main.xml)
- [Main Activity File](app/src/main/java/com/example/trivia/MainActivity.java): many of the special features mentioned above are directly implemented in this class.

### Demonstration of Application

<p align="center">
      <img src="https://github.com/kailongli27/Trivia/blob/master/app_recording.gif" width="350" height="600" title = "AppRecording">
</p
