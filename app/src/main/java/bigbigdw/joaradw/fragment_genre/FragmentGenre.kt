package bigbigdw.joaradw.fragment_genre

import android.content.Intent
import bigbigdw.joaradw.base.BookBaseFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import bigbigdw.joaradw.R

class FragmentGenre : BookBaseFragment() {

    var flayout_Wrap1 :FrameLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_genre, container, false)

        flayout_Wrap1 = root.findViewById(R.id.flayout_Wrap1)

        flayout_Wrap1!!.setOnClickListener{
            val intent = Intent(requireContext().applicationContext, Genre::class.java)
            startActivity(intent)
        }

        return root
    }

}