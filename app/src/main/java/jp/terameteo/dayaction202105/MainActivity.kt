package jp.terameteo.dayaction202105

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import jp.terameteo.dayaction202105.databinding.ActivityMainBinding

import jp.terameteo.dayaction202105.ui.main.MainFragmentStateAdapter

// TODO クリック時のイベント
// TODO ROOM 実装

class MainActivity : AppCompatActivity() {
    // androidx.fragment.app.Fragment Activity -> androidx.appcompat.app.AppCompatActivity
    private lateinit var binding: ActivityMainBinding
    private val viewModel:MainViewModel by viewModels() // activity-ktx

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initialize(this)
        
        // Bind Activity View
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val rewardLabel = binding.achievement
        rewardLabel.text = resources.getString(R.string.reward_placeHolder,viewModel.currentReward.value)

        val viewPager = binding.pager.apply {
            adapter = MainFragmentStateAdapter(this@MainActivity)
            currentItem = 10
            setPageTransformer(ZoomOutPageTransformer())
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                viewModel.currentPagePosition.postValue(position)
                Log.i("viewpager"," position = $position")
            }

        })
        val fab: FloatingActionButton = binding.fab

        viewModel.currentReward.observe(this){
            rewardLabel.text = resources.getString(R.string.reward_placeHolder,viewModel.currentReward.value)
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
//　あったが､現状では状態の保持はViewModelに委譲するのが主流｡
