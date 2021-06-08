package jp.terameteo.dayaction202105

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import jp.terameteo.dayaction202105.databinding.ActivityMainBinding
import jp.terameteo.dayaction202105.ui.main.MainViewModel
import jp.terameteo.dayaction202105.ui.main.SectionsPagerAdapter

// TODO クリック時のイベント
// TODO 自動カテゴリ分け タブ作成
// TODO ROOM 実装
// TODO

const val ARCHIVE_POINT = "archivePoint"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Bind Activity View
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dateLabelText = binding.labelDate
        val secretary = DataSecretary()
        dateLabelText.text = secretary.getTodayString()
        val achieveLabel = binding.achievement
        achieveLabel.text = resources.getString(R.string.reward_placeHolder,secretary.loadIntFromPreference(
            ARCHIVE_POINT,context = this))
        val viewPager = binding.pager
        viewPager.adapter = SectionsPagerAdapter(this)
        val tabLayout: TabLayout = binding.tabLayout
        val mediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "Tab  $position "
        }
        mediator.attach()
        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}