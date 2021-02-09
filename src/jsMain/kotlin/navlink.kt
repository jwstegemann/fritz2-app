import dev.fritz2.binding.Store
import dev.fritz2.binding.watch
import dev.fritz2.components.*
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.styling.*
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.styled
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@ExperimentalCoroutinesApi
open class NavLinkComponent {
    companion object {
        val activeStyle = staticStyle("navlink-active") {
            background { color { "rgba(0,0,0,0.2)" } }
            borders {
                left {
                    width { "0.2rem" }
                    color { lightGray }
                }
            }
        }
    }

    var icon = ComponentProperty<IconComponent.() -> Unit> { fromTheme { bookmark }}
    var text = DynamicComponentProperty<String>(flowOf("Navigation Link"))
    var active = ComponentProperty<Flow<Boolean>?>(null)
}

@ExperimentalCoroutinesApi
fun RenderContext.navLink(styling: BasicParams.() -> Unit = {},
                      baseClass: StyleClass? = null,
                      id: String? = null,
                      prefix: String = "navlink",
                      build: NavLinkComponent.() -> Unit = {}
) {
    val component = NavLinkComponent().apply(build)

    lineUp({
        paddings {
            vertical { "0.6rem" }
            horizontal { small }
        }
        alignItems { center }
        borders {
            left {
                width { "0.2rem" }
                color { "transparent" }
            }
        }
        styling()
    }, baseClass, id, prefix) {
        items {
            component.active.value?.let { className(NavLinkComponent.activeStyle.whenever(it).name) }
            icon({
                size { large }
                margins {
                    left { tiny }
                }
            }, build = component.icon.value)

            (::a.styled {
                display { block }
                fontWeight { semiBold }
                fontSize { normal }
            }) {
                component.text.values.asText()
            }
        }
    }
}

@ExperimentalCoroutinesApi
fun RenderContext.navSection(styling: BasicParams.() -> Unit = {},
                             text: String,
                          baseClass: StyleClass? = null,
                          id: String? = null,
                          prefix: String = "navsection",
                          build: NavLinkComponent.() -> Unit = {}
) {
    (::h3.styled {
        paddings {
            vertical { "0.5rem" }
            horizontal { small }
        }
        margins { top { small } }
        textTransform { uppercase }
        fontWeight { bold }
        fontSize { ".9rem" }
        color { gray }
    }) { +text }
}



