
#WARDROBE APP

This project focuses mainly on fetching images from gallery or clicking via camera and storing the file paths in the Sqllite Database.

The Home activity has two fragments inflated vertically(Portrait mode) & horizontally(landscape mode).
Each Fragment has ViewPager which is capable of showing images added from each of the following fragments.

With the help of rxjava it was possible to observe any changes made to the respective tables. If any change in the table was observed, a subscriber is attached which maps the query columns to the List of WardrobeItem and the adapter is updated.

Dagger is used to inject the DbModule, BriteDatabase.
ButterKnife was used to bind the views and listening to onClick listeners.
EventBus was used to listen to 'favourite' and 'shuffle' button clicks in the respective fragments where the events are registered.

On clicking on the favourite button a set of shirt and pant images will be stored in the shared preference(the file paths are stored). At 6am exactly a notification is received and on clicking on it an activity(NotificationActivity) is opened to show the chosen combination of images.
AlarmManager & AlarmReceiver was used to set the notification.

##Libraries Used :

butterknife  
dagger  
sqlbrite  
rxjava  
eventbus  
