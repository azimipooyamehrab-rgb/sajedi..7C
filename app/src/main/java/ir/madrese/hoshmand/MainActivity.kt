package ir.madrese.hoshmand

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

private val navy = androidx.compose.ui.graphics.Color(0xFF17324D)
private val blue = androidx.compose.ui.graphics.Color(0xFF2864A5)
private val pale = androidx.compose.ui.graphics.Color(0xFFF3F7FB)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = lightColorScheme(primary = blue, secondary = navy, background = pale)) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) { SchoolApp() }
            }
        }
    }
}

@Composable
fun SchoolApp() {
    var code by remember { mutableStateOf("") }
    var entered by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }
    var page by remember { mutableStateOf("خانه") }
    Surface(Modifier.fillMaxSize(), color = pale) {
        if (!entered) {
            Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🎓", style = MaterialTheme.typography.displayLarge)
                Text("مدرسه هوشمند", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = navy)
                Text("سامانه آموزشی ویژه افراد دعوت‌شده", textAlign = TextAlign.Center)
                Spacer(Modifier.height(24.dp))
                OutlinedTextField(value = code, onValueChange = { code = it; error = false }, label = { Text("کد دعوت آزمایشی") }, singleLine = true)
                if (error) Text("کد دعوت معتبر نیست", color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(12.dp))
                Button(onClick = { entered = code.trim() == "MADRESE-2026"; error = !entered }) { Text("ورود به سامانه") }
                Spacer(Modifier.height(12.dp))
                Text("نسخه آزمایشی؛ ورود واقعی باید از سرور امن تأیید شود.", textAlign = TextAlign.Center, style = MaterialTheme.typography.bodySmall)
            }
        } else {
            Column(Modifier.fillMaxSize()) {
                Column(Modifier.fillMaxWidth().padding(20.dp)) {
                    Text("مدرسه هوشمند", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = navy)
                    Text("خوش آمدید")
                }
                Row(Modifier.fillMaxWidth().padding(horizontal = 8.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf("خانه", "تکالیف", "برنامه", "پروفایل").forEach { FilterChip(selected = page == it, onClick = { page = it }, label = { Text(it) }) }
                }
                Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    when (page) {
                        "خانه" -> { CardInfo("📚 تکالیف", "تکالیف و مهلت تحویل در این بخش نمایش داده می‌شود."); CardInfo("🗓 برنامه هفتگی", "برنامه کلاس‌ها پس از اتصال به سرور نمایش داده می‌شود."); CardInfo("🔔 اعلان‌ها", "اعلان‌های مدرسه در این بخش قرار می‌گیرند.") }
                        "تکالیف" -> CardInfo("تکالیف من", "هنوز تکلیفی ثبت نشده است.")
                        "برنامه" -> CardInfo("برنامه هفتگی", "برنامه کلاس‌ها هنوز تنظیم نشده است.")
                        else -> { CardInfo("پروفایل", "اطلاعات و نقش کاربر پس از اتصال به سامانه امن نمایش داده می‌شود."); OutlinedButton(onClick = { entered = false; code = ""; page = "خانه" }) { Text("خروج") } }
                    }
                }
                Text("نسخه اولیه — نیازمند اتصال به سرور", Modifier.fillMaxWidth().padding(10.dp), textAlign = TextAlign.Center, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
@Composable
fun CardInfo(title: String, body: String) {
    ElevatedCard(Modifier.fillMaxWidth()) { Column(Modifier.padding(18.dp)) { Text(title, fontWeight = FontWeight.Bold, color = navy, style = MaterialTheme.typography.titleMedium); Spacer(Modifier.height(6.dp)); Text(body) } }
}
