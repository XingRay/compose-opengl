import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import javax.swing.BoxLayout
import javax.swing.JPanel

fun main() {
    // Load the library that contains the paint code.
//    System.loadLibrary("MyWindow")

    application {
        Window(onCloseRequest = ::exitApplication) {
            MyApp()
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier,
//    names: List<String> = listOf("World", "Compose")
) {
//    val properties = System.getProperties()
//    for (key in properties.keys()) {
//        println("key:${key}, value:${properties[key]}")
//    }

    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        if (shouldShowOnBoarding) {
            OnBoardingScreen(onContinueClicked = { shouldShowOnBoarding = false })
        } else {
            NativeSwingPanel()
//            Greetings()
        }
    }
}


@Composable
fun NativeSwingPanel() {
    SwingPanel(
        background = Color.White,
        modifier = Modifier.fillMaxSize(),
        factory = {
            JPanel().apply {
                setLayout(BoxLayout(this, BoxLayout.Y_AXIS))
                add(MyWindow())
            }
        }
    )
}

@Composable
fun OnBoardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("welcome to basic CodeLab")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text(text = "Continue")
        }
    }
}


@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { index -> "${index}" }
) {
    LazyColumn(modifier = modifier.padding(4.dp)) {
        items(items = names) { name ->
            Greeting(name)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Card(
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.primary
//        ),
        backgroundColor = MaterialTheme.colors.primary,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
fun CardContent(name: String) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(24.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1.0f)
                .padding(12.dp)
        ) {
            Text(text = "Hello")
            Text(
//                text = name, style = MaterialTheme.typography.headlineMedium.copy(
//                    fontWeight = FontWeight.ExtraBold
//                )
                text = name, style = MaterialTheme.typography.h3.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            val imagePainter = if (expanded) {
                painterResource("svg/up_arrow.svg")
            } else {
                painterResource("svg/down_arrow.svg")
            }

            Image(
                painter = imagePainter,
                contentDescription = if (expanded) {
                    stringResource("show_less")
                } else {
                    stringResource("show_more")
                },
                modifier = Modifier.size(width = 40.dp, height = 40.dp).clip(CircleShape)
            )
        }
    }
}
