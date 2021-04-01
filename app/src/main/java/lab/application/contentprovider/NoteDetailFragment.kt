package lab.application.contentprovider

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class NoteDetailFragment : DialogFragment(), DialogInterface.OnClickListener {

    private lateinit var noteEditTitle: EditText
    private lateinit var noteEditDescription: EditText

    private var id: Long = 0

    companion object {
        private const val EXTRA_ID = "id"

        fun newInstance(id: Long): NoteDetailFragment {
            val bundle = Bundle()
            bundle.putLong(EXTRA_ID, id)

            val notesFragment = NoteDetailFragment()
            notesFragment.arguments = bundle
            return notesFragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.note_detail, null)

        noteEditTitle = view?.findViewById(R.id.note_edt_title) as EditText
        noteEditDescription = view.findViewById(R.id.note_edt_description) as EditText

        return super.onCreateDialog(savedInstanceState)
    }

    override fun onClick(p0: DialogInterface?, p1: Int) {
        TODO("Not yet implemented")
    }
}
