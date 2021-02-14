package bj.archeos.eticketafrica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bj.archeos.eticketafrica.data.TinyDB
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialTextInputPicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var eticketData : TinyDB = TinyDB(applicationContext)
        val mono = findViewById<TextInputEditText>(R.id.mono)

        val duo = findViewById<MaterialButton>(R.id.duo)
        duo.setOnClickListener{
            //var valcode = mono.text.toString().toIntOrNull()
           // var categories = valcode
            val intent = Intent(this, MainValid::class.java)
            //intent.putExtra("Categories", categories)
            startActivity(intent)
            /*if (valcode != null) {
                eticketData.putInt("Categories", valcode)
                val intent = Intent(this, MainValid::class.java)
                intent.putExtra("Categories", categories)
                startActivity(intent)
            }else{
                Snackbar.make(it, "Codes incorrect", Snackbar.LENGTH_LONG)
                    .show()
            }*/
        }
    }
}