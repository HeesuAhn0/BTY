package com.example.mymultifragapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mymultifragapplication.databinding.ActivityMainBinding

val String.numOfKoreanCharacters : Int
    get() {
        var count = 0
        for (i in 0 until length){
            if ( this[i]>=0xAC00.toChar() && this[i] <= 0xD7AF.toChar())
                count +=1
        }
        return count
    }
class MainActivity : AppCompatActivity() {
    var text: String ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        fun replaceFragment(frag:Fragment) {
            supportFragmentManager.beginTransaction().run {
                replace(binding.frmFrag.id,frag)
                commit()
            }
        }
        binding.run {
            btnImput.setOnClickListener{
                replaceFragment(InputFragment.newInstance())
            }
            btnResult.setOnClickListener{
                replaceFragment(ResultFragment.newInstance(text?.numOfKoreanCharacters ?: 0))
            }
        }

        supportFragmentManager.setFragmentResultListener("input_text",this){_,bundle->
            text = bundle.getString("input")
        }

        replaceFragment(InputFragment.newInstance())

        supportFragmentManager.beginTransaction().run {
            commit()
        }

        setContentView(binding.root)
    }
}