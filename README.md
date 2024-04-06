# My Personal Project

## What will this application do?
- **This application is a basic color range filter for PNG files**
- The user will upload a photo to a folder and set parameters for the color range they want to change
- The program will then change all pixels within that color range into the selected color or transparent.

## Who will use it?
- Graphic designers and frontend developers who don't have access to costly software that does this function.
  - There is surprisingly little free tools that allow you to do this since most are locked behind a paywall
  - The user-friendliness of similar tools are quite poor and the results are messy

## Why is this project of interest to you?
- I develop websites and thus work *a lot* with logos, specifically translating them between dark and light mode variants
  - I couldn't find any easy-to-use free resources to do this job quickly.
  - Other free tools left messy marks on the outside of the objects I was trying to isolate
  - I am interested in making this project for myself to make my life easier when converting the backgrounds of images



## User Stories
- As a user, I want to be able to add multiple uploaded photos to a list of uploaded photos
- As a user, I want to be able to list all / only the selected photos within a list of uploaded photos
- As a user, I want to select 1 or more photos out of the list of photos and view them
- As a user, I want to be able to specify the range of the color filter
- As a user, I want to be able to preview and apply a color filter to selected photos
- As a user, I want to be able to have access to basic image tools (blur, invert, grayscale, etc.) 
- As a user, when I start the application, I want to be given the option to load my album from file.
- As a user, when I select the quit option from the application menu, I want to be reminded to save my album to file and have the option to do so or not.

## Instructions for Grader
- You can add multiple photos to the PhotoAlbum by opening a save file and pressing the "+" button on the left sidebar
- You can select photos that have been opened by pressing the "+" button on the right sidebar
- You can view selected photos by pressing the eye buttong on the right sidebar
- You can locate my visual component by opening a save with an image (lightning icon on the landing page)
- You can save the state of my application by pressing the save icon while in editor on the left navbar
- You can save the state of my application by pressing the load icon while in the landing page on the left navbar

## Phase 4: Task 2
Window Closing.

LOGS

Tue Apr 02 23:34:05 PDT 2024
Added Alphadigital (Bronze) (1) to album.

Tue Apr 02 23:34:05 PDT 2024
Displayed a list of all photos.

Tue Apr 02 23:34:05 PDT 2024
Selected Alphadigital (Bronze) (1) from album.

Tue Apr 02 23:34:05 PDT 2024
Displayed a list of all photos.

Tue Apr 02 23:34:05 PDT 2024
Displayed a list of all photos.

Tue Apr 02 23:34:09 PDT 2024
Added Calgary3D (Bronze) (1) to album.

Tue Apr 02 23:34:10 PDT 2024
Displayed a list of selected photos from all photos.

Tue Apr 02 23:34:15 PDT 2024
Displayed a list of all photos.

Tue Apr 02 23:34:17 PDT 2024
Displayed a list of all photos.

Tue Apr 02 23:34:17 PDT 2024
Selected Calgary3D (Bronze) (1) from album.

Tue Apr 02 23:34:18 PDT 2024
Displayed a list of all photos.

Tue Apr 02 23:34:20 PDT 2024
Displayed a list of all photos.

Tue Apr 02 23:34:20 PDT 2024
Deselected Alphadigital (Bronze) (1) from album.

Tue Apr 02 23:34:21 PDT 2024
Displayed a list of selected photos from all photos.

Tue Apr 02 23:34:22 PDT 2024
Displayed a list of all photos.

Tue Apr 02 23:34:24 PDT 2024
Inverted selected photos.

Tue Apr 02 23:34:29 PDT 2024
Displayed a list of all photos.

Tue Apr 02 23:34:43 PDT 2024
Displayed a list of all photos.

Tue Apr 02 23:34:43 PDT 2024
Displayed a list of all photos.

Tue Apr 02 23:34:43 PDT 2024
Displayed a list of selected photos from all photos.

## Phase 4: Task 3
**If you had more time to work on the project, what refactoring might you use to improve your design?**
I would either switch to a library like JavaFX or switch entirely to a tech stack more appropriate for this kind of
application. Using CSS to style rather than being bound by the limitations of Java Swing would really decrease
development time and make the UI related parts of the project much more readable. The more algorithmic parts of the 
project work just fine, but specifically refactoring the GUI to React/JS/HTML/CSS would be a massive improvement for 
future expansion. Additionally, I may consider refactoring my CLI class and method structure. A lot of very different
methods were shovelled into the Utils class, so for the sake of clarity, refactoring to abstract and couple similar
functions (like taking in a certain input) and differentiate different parts of the CLI tool would greatly improve
readablility, expandability, and just generally make my life easier when modifying or adding to the CLI. Just breaking
things up and merging similar methods together would be enough for a refactor to improve the structure and design
of the project as a whole.