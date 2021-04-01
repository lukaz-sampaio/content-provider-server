package lab.application.contentprovider

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.BaseColumns._ID
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import lab.application.contentprovider.database.NotesDatabaseHelper.Companion.TITLE_NOTES
import lab.application.contentprovider.database.NotesProvider.Companion.URI_NOTES

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    lateinit var noteRecycler: RecyclerView
    lateinit var noteAdd: FloatingActionButton

    lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteAdd = findViewById(R.id.note_add)
        noteAdd.setOnClickListener { }

        adapter = NotesAdapter(object : NoteClickedListener {
            override fun noteClickedItem(cursor: Cursor) {
                val id = cursor.getLong(cursor.getColumnIndex(_ID))
            }

            override fun noteRemoveItem(cursor: Cursor?) {
                val id = cursor?.getLong(cursor.getColumnIndex(_ID))
                contentResolver?.delete(Uri.withAppendedPath(URI_NOTES, id.toString()), null, null)
            }
        })
        adapter.setHasStableIds(true)

        noteRecycler = findViewById(R.id.notes_recycler)
        noteRecycler.layoutManager = LinearLayoutManager(this)
        noteRecycler.adapter = adapter
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            this, URI_NOTES, null, null, null, TITLE_NOTES
        )

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data != null) {
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }
}
