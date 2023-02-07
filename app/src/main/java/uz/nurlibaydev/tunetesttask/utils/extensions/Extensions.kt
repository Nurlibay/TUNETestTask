package uz.nurlibaydev.tunetesttask.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun View.visibility(visibility: Boolean): View {
    if (visibility) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
    return this
}

fun View.enabled(isEnabled: Boolean): View {
    this.isEnabled = isEnabled
    return this
}

fun Fragment.showMessage(msg: String?) {
    Toast.makeText(this.requireContext(), msg, Toast.LENGTH_LONG).show()
}

fun ViewGroup.inflate(@LayoutRes id: Int): View =
    LayoutInflater.from(context).inflate(id, this, false)

fun RecyclerView.addVertDivider(context: Context?) {
    this.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
}

fun RecyclerView.addHorizDivider(context: Context?) {
    this.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
}

fun View.onClick(onClick: (View) -> Unit) {
    this.setOnClickListener(onClick)
}

// Setting Html string to TextView and making links clickable
fun TextView.setTextViewHtml(html: String, onLinkClick: (link: String) -> Unit) {
    val sequence: CharSequence = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
    val strBuilder = SpannableStringBuilder(sequence)
    val urls = strBuilder.getSpans(0, sequence.length, URLSpan::class.java)
    for (span in urls) {
        makeLinkClickable(strBuilder, span, onLinkClick)
    }
    this.text = strBuilder
    this.movementMethod = LinkMovementMethod.getInstance()
}

private fun makeLinkClickable(
    strBuilder: SpannableStringBuilder,
    span: URLSpan,
    onLinkClick: (link: String) -> Unit,
) {
    val start = strBuilder.getSpanStart(span)
    val end = strBuilder.getSpanEnd(span)
    val flags = strBuilder.getSpanFlags(span)
    val clickable = object : ClickableSpan() {
        override fun onClick(widget: View) {
            val fullText = (widget as TextView).text.toString()
            val link = fullText.substring(start, end)
            onLinkClick.invoke(link)
        }
    }
    strBuilder.setSpan(clickable, start, end, flags)
    strBuilder.removeSpan(span)
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Int.dpToFloat: Float
    get() = (this / Resources.getSystem().displayMetrics.density)

fun String.dialPhone(activity: Activity) {
    val phone = "+998$this"
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(phone)))
    activity.startActivity(intent)
}

fun String.dialPhoneFull(activity: Activity) {
    val phone = this
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(phone)))
    activity.startActivity(intent)
}

val String.ifContainsLatin: Boolean
    get() {
        this.forEach {
            if (it.code in 65..90 || it.code in 97..122) {
                return true
            }
        }
        return false
    }

val String.toSumFormat: String
    get() {
        var text = this.reversed()
        text = text.subSequence(0, text.length)
            .chunked(3) // group every 3 chars
            .joinToString(" ")
        return text.reversed()
    }


val Int.toSumFormat: String
    get() {
        var text = this.toString().reversed()
        text = text.subSequence(0, text.length)
            .chunked(3) // group every 3 chars
            .joinToString(" ")
        return text.reversed()
    }

val Number.toSumFormat: String
    get() {
        var text = this.toString().reversed()
        text = text.subSequence(0, text.length)
            .chunked(3) // group every 3 chars
            .joinToString(" ")
        return text.reversed()
    }

val Double.toSumFormat: String
    get() {
        var num = this.toLong().toSumFormat
        val l = this.toString().length - this.toLong().toString().length - 1
        val formattedNum = "%.${2}f".format(this)
        val afterPoint = formattedNum.substring(formattedNum.length - 2, formattedNum.length)
        num += if (afterPoint == "0") ".00" else {
            if (afterPoint.length == 1) ".${afterPoint}0"
            else ".$afterPoint"
        }
        return num
    }

val String.toPhoneNumber: String
    get() {
        val arr = this.toCharArray()
        var phone = "+998 ("
        arr.forEachIndexed { index, c ->
            phone += c
            if (index == 1) {
                phone += ") "
            }
            if (index == 4 || index == 6) {
                phone += " "
            }
        }
        return phone
    }

val String.toPhoneNumberFromFull: String
    get() {
        val arr = this.substring(4..this.lastIndex).toCharArray()
        var phone = "+998 ("
        arr.forEachIndexed { index, c ->
            phone += c
            if (index == 1) {
                phone += ") "
            }
            if (index == 4 || index == 6) {
                phone += " "
            }
        }
        return phone
    }

/**
 * Change date format from _yyyy-MM-dd_ to _dd.MM.yyyy_ and vice versa
 */
val String.changeDateFormat: String
    get() {
        val day: String
        val month: String
        val year: String
        return if (this.contains('-')) {
            day = this.substring(8..9)
            month = this.substring(5..6)
            year = this.substring(0..3)
            "$day.$month.$year"
        } else {
            day = this.substring(0..1)
            month = this.substring(3..4)
            year = this.substring(6..9)
            "$year-$month-$day"
        }
    }

fun String.getOnlyDigits(): String {
    val s = this.filter { it.isDigit() }
    return s.ifEmpty { "0" }
}