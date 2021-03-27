package lab.application.contentprovider.database

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.media.UnsupportedSchemeException
import android.net.Uri
import android.provider.BaseColumns
import android.provider.BaseColumns._ID

class NotesProvider : ContentProvider() {

    lateinit var uriMatcher: UriMatcher
    lateinit var dbHelper: NotesDatabaseHelper

    override fun onCreate(): Boolean {
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher.addURI(AUTHORITY, "notes", NOTES)
        uriMatcher.addURI(AUTHORITY, "notes/#", NOTES_BY_ID)

        if (context != null) {
            dbHelper = NotesDatabaseHelper(context as Context)
        }

        return true
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        if (uriMatcher.match(uri) == NOTES_BY_ID) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val linesAffected = db.delete(
                NotesDatabaseHelper.TABLE_NOTES,
                "${BaseColumns._ID} = ? ",
                arrayOf(uri.lastPathSegment)
            )
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return linesAffected
        } else {
            throw UnsupportedSchemeException("URL inválida pra exclusão")
        }
    }

    override fun getType(uri: Uri): String? =
        throw UnsupportedSchemeException("Not implemented yet!")

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (uriMatcher.match(uri) == NOTES_BY_ID) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val id = db.insert(
                NotesDatabaseHelper.TABLE_NOTES,
                null, values)
            val insertUri = Uri.withAppendedPath(BASE_URI, id.toString())
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return insertUri
        } else {
            throw UnsupportedSchemeException("URL inválida pra exclusão")
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when {
            uriMatcher.match(uri) == NOTES -> {
                val db = dbHelper.writableDatabase
                val cursor = db.query(NotesDatabaseHelper.TABLE_NOTES, projection, selection, selectionArgs, null, null, sortOrder)
                cursor.setNotificationUri(context?.contentResolver, uri)
                cursor
            }
            uriMatcher.match(uri) == NOTES_BY_ID -> {
                val db = dbHelper.writableDatabase
                val cursor = db.query(NotesDatabaseHelper.TABLE_NOTES, projection, "$_ID = ?", arrayOf(uri.lastPathSegment), null, null, sortOrder)
                cursor.setNotificationUri((context as Context).contentResolver, uri)
                cursor
            } else -> {
                throw UnsupportedSchemeException("Uri not implemented yet!")
            }
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        if (uriMatcher.match(uri) == NOTES_BY_ID) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val linesAffected = db.update(
                NotesDatabaseHelper.TABLE_NOTES,
                values, "$_ID = ?", arrayOf(uri.lastPathSegment))
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return linesAffected
        } else {
            throw UnsupportedSchemeException("URL inválida pra atualização")
        }
    }

    companion object {
        const val AUTHORITY = "lab.application.contentprovider.provider"
        val BASE_URI = Uri.parse("content://$AUTHORITY")
        val URI_NOTES = Uri.withAppendedPath(BASE_URI, "notes")

        const val NOTES = 1
        const val NOTES_BY_ID = 2
    }
}
