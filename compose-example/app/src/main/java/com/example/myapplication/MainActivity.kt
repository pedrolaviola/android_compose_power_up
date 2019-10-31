package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.core.content.contentValuesOf
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.setContent
import androidx.ui.engine.geometry.Radius
import androidx.ui.engine.geometry.Shape
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.SimpleImage
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.surface.Card
import androidx.ui.material.surface.Surface
import androidx.ui.res.imageResource
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview


data class Contact(
    val id: Int,
    val name: String,
    var isFavorite: Boolean = false
)

@Model
object ContactList {
    var list = emptyList<Contact>()

    fun toggleFavorite(contact: Contact) {
        list = list.map {
            if (it.id == contact.id) {
                contact.copy(isFavorite = !contact.isFavorite)
            } else {
                it
            }
        }
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateView()
        }
    }
}

@Preview
@Composable
fun CreateView() {
    val count = +state { 1 }
    MaterialTheme(colors = lightThemeColors) {
        Surface(color = +themeColor { background }) {
            Column(
                crossAxisSize = LayoutSize.Expand,
                modifier = Spacing(20.dp)
            ) {
                Row(
                    mainAxisSize = LayoutSize.Expand,
                    mainAxisAlignment = MainAxisAlignment.SpaceBetween
                ) {
                    Button(
                        text = +stringResource(R.string.add_btn_text),
                        onClick = {
                            ContactList.list += Contact(
                                count.value,
                                "Contato ${count.value}"
                            )
                            count.value++
                        })
                    Button(
                        text = +stringResource(R.string.remove_btn_text),
                        onClick = {
                            count.value--
                            ContactList.list -= ContactList.list[ContactList.list.lastIndex]
                        },
                        style = TextButtonStyle()
                    )
                }
                ContactList.list.forEach() {
                    ContactCard(contact = it)
                }
            }
        }
    }
}


@Composable
fun ContactCard(contact: Contact) {
    HeightSpacer(height = 20.dp)
    Card(shape = RoundedCornerShape(12.dp), elevation = 8.dp) {
        Clickable(onClick = { ContactList.toggleFavorite(contact = contact) }) {
            Row(
                modifier = Spacing(12.dp),
                mainAxisSize = LayoutSize.Expand
            ) {
                FavoriteImage(isFavorite = contact.isFavorite)
                WidthSpacer(width = 8.dp)
                Text(text = contact.name, style = +themeTextStyle { body1 })
            }
        }
    }
}

@Composable
fun FavoriteImage(isFavorite: Boolean) {
    SimpleImage(
        image = if (isFavorite) {
            +imageResource(R.drawable.ic_like)
        } else {
            +imageResource(R.drawable.ic_unlike)
        }
    )
}

