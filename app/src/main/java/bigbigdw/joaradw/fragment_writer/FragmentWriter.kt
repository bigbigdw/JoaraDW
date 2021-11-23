package bigbigdw.joaradw.fragment_writer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import bigbigdw.joaradw.R
import bigbigdw.joaradw.writer.RetrofitWriter
import bigbigdw.joaradw.writer.WriterBookCountResult
import bigbigdw.joaradw.writer.WriterMemberLevelResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentWriter : Fragment() {

    lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_writer, container, false)

        setLayout()

        return root
    }

    fun setLayout(){

        val token = requireContext().getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("TOKEN", "")

        RetrofitWriter.getWriterLevel(token,context)!!
            .enqueue(object : Callback<WriterMemberLevelResult?> {
                override fun onResponse(
                    call: Call<WriterMemberLevelResult?>,
                    response: Response<WriterMemberLevelResult?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { it ->
                            val icon = it.member_level?.icon
                            val level = it.member_level?.level
                            val pen_exp = it.member_level?.pen_exp
                            val pen_name = it.member_level?.pen_name

                        }
                    }
                }

                override fun onFailure(call: Call<WriterMemberLevelResult?>, t: Throwable) {
                    Log.d("onFailure", "실패")
                }
            })
    }
}