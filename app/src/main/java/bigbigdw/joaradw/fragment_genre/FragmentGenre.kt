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

    private var flayout_Wrap1 :FrameLayout? = null
    private var flayout_Wrap2 :FrameLayout? = null
    private var flayout_Wrap3 :FrameLayout? = null
    private var flayout_Wrap4 :FrameLayout? = null
    private var flayout_Wrap5 :FrameLayout? = null
    private var flayout_Wrap6 :FrameLayout? = null
    private var flayout_Wrap7 :FrameLayout? = null
    private var flayout_Wrap8 :FrameLayout? = null
    private var flayout_Wrap9 :FrameLayout? = null
    private var flayout_Wrap10 :FrameLayout? = null
    private var flayout_Wrap11 :FrameLayout? = null
    private var flayout_Wrap12 :FrameLayout? = null
    private var flayout_Wrap13 :FrameLayout? = null
    private var flayout_Wrap14 :FrameLayout? = null
    private var flayout_Wrap15 :FrameLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_genre, container, false)

        flayout_Wrap1 = root.findViewById(R.id.flayout_Wrap1)
        flayout_Wrap2 = root.findViewById(R.id.flayout_Wrap2)
        flayout_Wrap3 = root.findViewById(R.id.flayout_Wrap3)
        flayout_Wrap4 = root.findViewById(R.id.flayout_Wrap4)
        flayout_Wrap5 = root.findViewById(R.id.flayout_Wrap5)
        flayout_Wrap6 = root.findViewById(R.id.flayout_Wrap6)
        flayout_Wrap7 = root.findViewById(R.id.flayout_Wrap7)
        flayout_Wrap8 = root.findViewById(R.id.flayout_Wrap8)
        flayout_Wrap9 = root.findViewById(R.id.flayout_Wrap09)
        flayout_Wrap10 = root.findViewById(R.id.flayout_Wrap10)

        flayout_Wrap11 = root.findViewById(R.id.flayout_Wrap11)
        flayout_Wrap12 = root.findViewById(R.id.flayout_Wrap12)
        flayout_Wrap13 = root.findViewById(R.id.flayout_Wrap13)
        flayout_Wrap14 = root.findViewById(R.id.flayout_Wrap14)
        flayout_Wrap15 = root.findViewById(R.id.flayout_Wrap15)

        flayout_Wrap1!!.setOnClickListener{
            val intent = Intent(requireContext().applicationContext, Genre::class.java)
            intent.putExtra("CATEGORY", "1")
            startActivity(intent)
        }

        flayout_Wrap2!!.setOnClickListener{
            val intent = Intent(requireContext().applicationContext, Genre::class.java)
            intent.putExtra("CATEGORY", "2")
            startActivity(intent)
        }

        flayout_Wrap3!!.setOnClickListener{
            val intent = Intent(requireContext().applicationContext, Genre::class.java)
            intent.putExtra("CATEGORY", "3")
            startActivity(intent)
        }

        flayout_Wrap4!!.setOnClickListener{
            val intent = Intent(requireContext().applicationContext, Genre::class.java)
            intent.putExtra("CATEGORY", "4")
            startActivity(intent)
        }

        flayout_Wrap5!!.setOnClickListener{
            val intent = Intent(requireContext().applicationContext, Genre::class.java)
            intent.putExtra("CATEGORY", "5")
            startActivity(intent)

        }

        flayout_Wrap6!!.setOnClickListener{
            val intent = Intent(requireContext().applicationContext, Genre::class.java)
            intent.putExtra("CATEGORY", "22")
            startActivity(intent)
        }

        flayout_Wrap7!!.setOnClickListener{
            val intent = Intent(requireContext().applicationContext, Genre::class.java)
            intent.putExtra("CATEGORY", "20")
            startActivity(intent)
        }

        flayout_Wrap8!!.setOnClickListener{
            val intent = Intent(requireContext().applicationContext, Genre::class.java)
            intent.putExtra("CATEGORY", "23")
            startActivity(intent)
        }

        flayout_Wrap9!!.setOnClickListener{
            val intent = Intent(requireContext().applicationContext, Genre::class.java)
            intent.putExtra("CATEGORY", "21")
            startActivity(intent)
        }

        flayout_Wrap10!!.setOnClickListener{
            val intent = Intent(requireContext().applicationContext, Genre::class.java)
            intent.putExtra("CATEGORY", "12")
            startActivity(intent)
        }

        flayout_Wrap11!!.setOnClickListener{
            val intent = Intent(requireContext().applicationContext, Genre::class.java)
            intent.putExtra("CATEGORY", "9")
            startActivity(intent)
        }

        flayout_Wrap12!!.setOnClickListener{
            val intent = Intent(requireContext().applicationContext, Genre::class.java)
            intent.putExtra("CATEGORY", "6")
            startActivity(intent)
        }

        flayout_Wrap13!!.setOnClickListener{
            val intent = Intent(requireContext().applicationContext, Genre::class.java)
            intent.putExtra("CATEGORY", "19")
            startActivity(intent)
        }

        flayout_Wrap14!!.setOnClickListener{
            val intent = Intent(requireContext().applicationContext, Genre::class.java)
            intent.putExtra("CATEGORY", "60,11,7,15,8,14")
            startActivity(intent)
        }

        flayout_Wrap15!!.setOnClickListener{
            val intent = Intent(requireContext().applicationContext, Genre::class.java)
            intent.putExtra("CATEGORY", "50,10,18,13,16,17")
            startActivity(intent)
        }


        return root
    }

}