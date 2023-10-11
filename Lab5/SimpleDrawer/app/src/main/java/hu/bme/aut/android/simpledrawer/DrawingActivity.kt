package hu.bme.aut.android.simpledrawer

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import androidx.appcompat.app.AlertDialog
import hu.bme.aut.android.simpledrawer.databinding.ActivityDrawingBinding
import hu.bme.aut.android.simpledrawer.sqlite.PersistentDataHelper
import hu.bme.aut.android.simpledrawer.view.DrawingView

class DrawingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrawingBinding
    private lateinit var dataHelper: PersistentDataHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataHelper = PersistentDataHelper(this)
        dataHelper.open()
        restorePersistedObjects()
    }
    override fun onResume() {
        super.onResume()
        dataHelper.open()
    }

    override fun onPause() {
        dataHelper.close()
        super.onPause()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage(R.string.are_you_sure_want_to_exit)
            .setPositiveButton(R.string.ok) { _, _ -> onExit() }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun onExit() {
        dataHelper.persistPoints(binding.canvas.points)
        dataHelper.persistLines(binding.canvas.lines)
        dataHelper.close()
        finish()
    }

    private fun restorePersistedObjects() {
        binding.canvas.restoreObjects(dataHelper.restorePoints(), dataHelper.restoreLines())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val toolbarMenu: Menu = binding.toolbar.menu
        menuInflater.inflate(R.menu.menu_toolbar, toolbarMenu)
        for (i in 0 until toolbarMenu.size()) {
            val menuItem: MenuItem = toolbarMenu.getItem(i)
            menuItem.setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
            if (menuItem.hasSubMenu()) {
                val subMenu: SubMenu = menuItem.subMenu!!
                for (j in 0 until subMenu.size()) {
                    subMenu.getItem(j)
                        .setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
                }
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_style_line -> {
                binding.canvas.currentDrawingStyle = DrawingView.DRAWING_STYLE_LINE
                item.isChecked = true
                true
            }
            R.id.menu_style_point -> {
                binding.canvas.currentDrawingStyle = DrawingView.DRAWING_STYLE_POINT
                item.isChecked = true
                true
            }
            R.id.menu_canvas_clear -> {
                dataHelper.clearLines()
                dataHelper.clearPoints()
                restorePersistedObjects()
                true
            }
            R.id.menu_color_red -> {
                binding.canvas.setColor(Color.RED)
                item.isChecked = true
                true
            }
            R.id.menu_color_green -> {
                binding.canvas.setColor(Color.GREEN)
                item.isChecked = true
                true
            }
            R.id.menu_color_blue -> {
                binding.canvas.setColor(Color.BLUE)
                item.isChecked = true
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
// NEPTUN: JOYAXJ