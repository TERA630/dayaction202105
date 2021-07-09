package jp.terameteo.dayaction202105

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.terameteo.dayaction202105.databinding.ActivityMainBinding
import jp.terameteo.dayaction202105.ui.main.MainFragmentStateAdapter

// TODO ROOM 実装

const val DETAIL_WINDOW = "detailWindow"

class MainActivity : AppCompatActivity() {
    // androidx.fragment.app.Fragment Activity -> androidx.appcompat.app.AppCompatActivity
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels() // activity-ktx

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initialize(this)

        // Bind Activity View
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val rewardLabel = binding.achievement
        rewardLabel.text = resources.getString(R.string.reward_placeHolder, viewModel.currentReward.value)

        binding.pager.apply {
            adapter = MainFragmentStateAdapter(this@MainActivity)
            currentItem = 10 // position 0-9
            setPageTransformer(ZoomOutPageTransformer())
        }
        // アイテム追加の準備
        val fab: FloatingActionButton = binding.fab
        fab.setOnClickListener {

        }
        viewModel.currentRewardStr.observe(this) {
            rewardLabel.text = it
        }
    }
    override fun onPause() {
        viewModel.stateSave(this)
        super.onPause()
    }

}
//　Activityのみでアプリケーションを完結させようとすると､
//　端末の回転時やアプリケーションの切り替え時に表示状態(State)の保持に苦労する｡
//　2017年頃までには回転させない､Activity再生成させない､onSavedInstanceStateを使うなど
//　あったが､状態の保持はViewModelクラスで行うようにするのが主流｡
