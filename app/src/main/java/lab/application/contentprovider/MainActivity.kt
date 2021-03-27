package lab.application.contentprovider

import android.database.Cursor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import lab.application.contentprovider.database.NotesDatabaseHelper
import lab.application.contentprovider.database.NotesProvider

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    lateinit var noteRecycler: RecyclerView
    lateinit var noteAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteRecycler = findViewById(R.id.notes_recycler)
        noteAdd = findViewById(R.id.note_add)
        noteAdd.setOnClickListener {  }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            this,
            NotesProvider.URI_NOTES,
            null,
            null,
            null,
            NotesDatabaseHelper.TITLE_NOTES
        )

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }
}
