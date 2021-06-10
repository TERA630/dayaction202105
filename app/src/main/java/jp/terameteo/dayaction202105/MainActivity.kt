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

// TODO ROOM 実装

const val ARCHIVE_POINT = "archivePoint"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel:MainViewModel by viewModels() // activity-ktx

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initialize(this)
        
        // Bind Activity View
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dateLabelText = binding.labelDate
        dateLabelText.text = viewModel.currentDate
        val achieveLabel = binding.achievement
        achieveLabel.text = resources.getString(R.string.reward_placeHolder,viewModel.currentReward.value)

       val viewPager = binding.pager
       viewPager.adapter = SectionsPagerAdapter(this,viewModel.currentCategory.size)
        val tabLayout: TabLayout = binding.tabLayout
        val mediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = viewModel.currentCategory[position]
        }
         mediator.attach()
        val fab: FloatingActionButton = binding.fab

        viewModel.currentReward.observe(this){
            achieveLabel.text = resources.getString(R.string.reward_placeHolder,viewModel.currentReward.value)
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
    override fun onPause() {
        super.onPause()
        viewModel.stateSave(this)
    }
}
//　Activityのみでアプリケーションを完結させようとすると､
//　端末の回転時やアプリケーションの切り替え時に表示状態(State)の保持に苦労する｡
//　2017年頃までには回転させない､Activity再生成させない､onSavedInstanceStateを使うなど
//　あったが､現状では状態の保持はViewModelにおくのが主流｡

//　Bind
//　Observe　が　OnCreateに全て記載｡　DataBindingとすれば回避できるかも
