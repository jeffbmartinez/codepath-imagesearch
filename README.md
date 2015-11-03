# Assignment 2 - *Image Search*

**Image Search** is an android app that allows you to search for images on your phone.

It is [Assignment #2](http://courses.codepath.com/courses/intro_to_android/unit/2#!assignment) for the [CodePath Android class](https://codepath.com/androidbootcamp).

Submitted by: **Jeff Martinez**

Time spent: ~**5** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can enter a search query that will display a grid of image results from the Google Image API.
* [ ] User can click on "settings" which allows selection of advanced search options to filter results
* [ ] User can configure advanced search filters such as:
    * [ ] Size (small, medium, large, extra-large)
    * [ ] Color filter (black, blue, brown, gray, green, etc...)
    * [ ] Type (faces, photo, clip art, line art)
    * [ ] Site (espn.com)
* [ ] Subsequent searches will have any filters applied to the search results
* [X] User can tap on any image in results to see the image full-screen
* [ ] User can [scroll down “infinitely”](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews) to continue loading more image results (up to 8 pages)

The following **optional** features are implemented:

* [ ] **Advanced**: Robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [ ] **Advanced**: Use the [ActionBar SearchView](http://guides.codepath.com/android/Extended-ActionBar-Guide#adding-searchview-to-actionbar) or custom layout as the query box instead of an EditText
* [ ] **Advanced**: User can [share an image](http://guides.codepath.com/android/Sharing-Content-with-Intents) to their friends or email it to themselves
* [ ] **Advanced**: Replace Filter Settings Activity with a lightweight [modal overlay](http://guides.codepath.com/android/Using-DialogFragment)
* [ ] **Advanced**: Improve the user interface and experiment with image assets and/or styling and coloring
* [ ] **Bonus**: Use the [StaggeredGridView](https://github.com/f-barth/AndroidStaggeredGrid) to display improve the grid of image results
* [ ] **Bonus**: User can [zoom or pan images](https://github.com/MikeOrtiz/TouchImageView) displayed in full-screen detail view

The following **additional** features are implemented:

* [X] The app stores previously selected search filters even after the app is turned off, using `SharedPreferences`.

## Video Walkthrough

With required functionality:

![Video Walkthrough 1](walkthrough.gif)

With additional functionality:

![Video Walkthrough 2](walkthrough2.gif)

GIFs created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

### Challenges encountered

* `getActionBar().hide()` was throwing a null pointer exception for me. The fix was to use `getSupport* ActionBar().hide()` instead, which from what I've read, is the safer way to do this.
* I tried changing the type of the data I was storing with SharedPreferences at some point during development. When I tried retrieving the data with a key of a new type, I was getting a type conversion error. It took me a bit of troubleshooting before I realized it was just reusing the previously stored key. I programmitically cleared the old stored data with `clear()` and the app worked as expected.
* I can't seem to get `imgcolor` and `imgtype` to work as expected. Regardless of the argumenet passed in, the api behaves as if it's not there.

## Open Source Libraries Used

* [James Smith's Android Asynchronous Http Client](http://loopj.com/android-async-http/)
* [Square's Picasso](http://square.github.io/picasso/)

## License

    The MIT License (MIT)

	Copyright (c) 2015 jeff martinez
	
	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:
	
	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.
	
	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
