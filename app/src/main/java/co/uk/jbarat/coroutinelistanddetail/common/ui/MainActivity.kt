package co.uk.jbarat.coroutinelistanddetail.common.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.findNavController
import co.uk.jbarat.coroutinelistanddetail.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onBackPressed() {
        if (!findNavController(R.id.navigationHost).popBackStack()) {
            super.onBackPressed()
        }
    }
}
