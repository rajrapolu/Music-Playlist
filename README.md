INTRODUCTION

This project presents a theme for a music playlist application which helps the users to create new playlist and add, remove songs to it.


SUPPORT

The project has a minSDK of API level 16, and a targetSDK of API level 25. It supports both mobiles and tablets by offering a different UI based on the device used.


FEATURES

The project offers the following features:

  •	Displays a list of playlists.

  •	Create new playlist (playlists name should be unique i.e., if the user tries to create a playlist with existing name of a playlist in the project it does not accept the entry).

  •	On tapping a playlist, the songs that are present in the playlist are displayed. If the playlist is empty then an empty view is presented.

  •	Add songs to playlist. On clicking add songs or fab button based on UI, three dummy songs are added to the playlist.

  •	Delete a song from playlist. By clicking the delete image view a song can be deleted from the playlist.


Tools Used

  •	SQLite database is used along with a content provider to provide a persistent storage for playlists and songs.

  •	Used Cursor Loaders to query the database and update the UI if there are any changes to the database.

  •	Used Recycler View to display the list of playlists and songs in the playlist.

  •	Used ButterKnife to bind the views.

  •	Used Android Lint to optimize the code.

