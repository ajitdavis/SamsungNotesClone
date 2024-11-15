# Samsung Notes Clone

Samsung Notes Clone is an Android application that replicates the basic functionality of the Samsung Notes app. It allows users to create, view, and manage notes using modern Android development practices, including MVVM architecture, data binding, and Jetpack components.

## Features

- **View Notes**: Display all saved notes in a RecyclerView with a grid layout.
- **Create and Edit Notes**: Add new notes and edit existing ones.
- **Delete Notes**: Select and delete multiple notes using selection mode.
- **Navigation**: Navigate between main and detail views using the Navigation Component.
- **MVVM Architecture**: Ensures a clean separation of concerns.
- **Data Binding**: Simplifies UI updates.
- **Room Database**: Persistent storage for notes.
- **Material Design**: Follows Android's Material Design guidelines for a consistent and modern UI/UX.

## Screenshots

*Include relevant screenshots of the app here.*

## Project Structure

```
├── view
│   ├── MainActivity.kt
│   ├── NotesFragment.kt
│   ├── NotesAdapter.kt
│
├── viewmodel
│   ├── NotesViewModel.kt
│
├── model
│   ├── Note.kt
│   ├── NoteDao.kt
│   ├── NoteDatabase.kt
│
├── navigation
│   ├── nav_graph.xml
│
├── res
│   ├── layout
│   │   ├── activity_main.xml
│   │   ├── fragment_notes.xml
│   ├── menu
│   │   ├── menu_main.xml
│   ├── values
│   │   ├── strings.xml
│   │   ├── colors.xml
│   ├── drawable
│
├── AndroidManifest.xml
```

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/samsung-notes-clone.git
   ```

2. **Open in Android Studio**:
   - Import the project into Android Studio.

3. **Build the project**:
   - Sync Gradle and build the project.

4. **Run the app**:
   - Use an emulator or physical device to run the app.

## Usage

- **Viewing Notes**: The app opens to a list of all saved notes.
- **Creating a Note**: Click the floating action button to add a new note.
- **Editing a Note**: Tap on an existing note to open it in edit mode.
- **Deleting Notes**: Long-press to enter selection mode, select notes, and press the delete button in the bottom app bar.

## Code Overview

### MainActivity
- Hosts the main UI components and manages the RecyclerView.
- Handles navigation to the `NotesFragment`.

### NotesAdapter
- Binds note data to the RecyclerView.
- Supports item selection for deletion.

### NotesViewModel
- Provides data to the UI and handles business logic.

### Room Database
- `NoteDatabase` provides an instance of the database.
- `NoteDao` defines database operations.

## Dependencies

```gradle
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'androidx.recyclerview:recyclerview:1.3.0'
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0'
implementation 'androidx.navigation:navigation-fragment-ktx:2.7.0'
implementation 'androidx.navigation:navigation-ui-ktx:2.7.0'
implementation 'androidx.room:room-runtime:2.5.2'
kapt 'androidx.room:room-compiler:2.5.2'
implementation 'com.google.android.material:material:1.10.0'
```

## Future Enhancements
- Add note categories or tags.
- Implement search functionality.
- Enhance UI/UX with more customization options.

## License
This project is licensed under the MIT License.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## Acknowledgements
- Inspired by the Samsung Notes app.
- Uses Jetpack components and Material Design by Google.
