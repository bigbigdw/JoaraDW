package bigbigdw.joaradw.test

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import bigbigdw.joaradw.R

class FragmentTest : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.test, container, false)
    }
}