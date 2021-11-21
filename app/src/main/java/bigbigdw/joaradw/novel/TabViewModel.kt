package bigbigdw.joaradw.novel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

class TabViewModel : ViewModel() {
    private val mIndex = MutableLiveData<Int>()
    val text = Transformations.map(mIndex) { input: Int -> "TAB$input" }
    fun setIndex(index: Int) {
        mIndex.value = index
    }
}