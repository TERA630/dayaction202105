package jp.terameteo.dayaction202105

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.terameteo.dayaction202105.databinding.ActivityMainBinding
import jp.terameteo.dayaction202105.ui.main.HistoryFragment
import jp.terameteo.dayaction202105.ui.main.MainFragmentStateAdapter

const val DETAIL_WINDOW = "detailWindow"
const val HISTORY_WINDOW = "historyWindow"

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
        binding.toHistory.setOnClickListener{
            wakeHistoryFragment()
        }
        viewModel.currentRewardStr.observe(this) {
            rewardLabel.text = it
        }
    }
    override fun onPause() {
        viewModel.stateSave(this)
        super.onPause()
    }
    private fun wakeHistoryFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragmentOrNull =
            supportFragmentManager.findFragmentByTag(HISTORY_WINDOW) as HistoryFragment?
        if (fragmentOrNull == null) {
            // Fragmentがまだインスタンス化されてなければ(初回起動)
            val fragment = HistoryFragment.newInstance()
            transaction.add(fragment, HISTORY_WINDOW)
            transaction.replace(R.id.scroll_content,fragment)
        } else {
            // detailFragmentがインスタンス化されていたら
            if (fragmentOrNull.isVisible) {
                transaction.hide(fragmentOrNull)
            } else {
                transaction.show(fragmentOrNull)
            }
        }
        transaction.commit()
    }
}
//　Activityのみでアプリケーションを完結させようとすると､
//　端末の回転時やアプリケーションの切り替え時に表示状態(State)の保持に苦労する｡
//　2017年頃までには回転させない､Activity再生成させない､onSavedInstanceStateを使うなど
//　あったが､状態の保持はViewModelクラスで行うようにするのが主流｡
