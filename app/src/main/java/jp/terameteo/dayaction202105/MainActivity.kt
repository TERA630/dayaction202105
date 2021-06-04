package jp.terameteo.dayaction202105

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import jp.terameteo.dayaction202105.databinding.ActivityMainBinding
import jp.terameteo.dayaction202105.ui.main.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dateLabelText = binding.labelDate
        dateLabelText.text = getString(R.string.date_placeHolder,"2021","6","3")
        val viewPager = binding.pager
        viewPager.adapter = SectionsPagerAdapter(this)
        val tabLayout: TabLayout = binding.tabLayout
        val mediator = TabLayoutMediator(tabLayout,viewPager) { tab, position -> tab.text = "Tab {$position + 1}"}
        mediator.attach()
        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}