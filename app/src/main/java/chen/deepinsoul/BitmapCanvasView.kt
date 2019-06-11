package chen.deepinsoul

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BitmapCanvasView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private var mBmp: Bitmap? = null
    private var mPaint: Paint? = null
    private var mBmpCanvas: Canvas? = null

    init {
        mPaint = Paint()
        mPaint!!.color = Color.BLACK
        mBmp = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
        mBmpCanvas = Canvas(mBmp!!)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint!!.textSize = 50f
        mBmpCanvas!!.drawText("欢迎光临", 0f, 100f, mPaint!!)
        canvas.drawBitmap(mBmp!!, 0f, 0f, mPaint)
    }

}
