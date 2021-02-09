import dev.fritz2.binding.RootStore
import dev.fritz2.binding.SimpleHandler
import dev.fritz2.binding.Store
import dev.fritz2.binding.storeOf
import dev.fritz2.components.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.html.TextElement
import dev.fritz2.styling.StyleClass
import dev.fritz2.styling.name
import dev.fritz2.styling.params.*
import dev.fritz2.styling.staticStyle
import dev.fritz2.styling.theme.Property
import dev.fritz2.styling.theme.Theme
import dev.fritz2.styling.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
object PwaStyles {
    val headerHeight: Property = "3.6rem"

    val brand: Style<FlexParams> = {
        //background { color { "rgb(44, 49, 54)"} }
        background { color { primary } }
        padding { small }
        color { lighterGray }
        alignItems { center }
        borders {
            bottom {
                width { "1px " }
                style { solid }
                color { gray }
            }
        }
    }

    val sidebar: Style<BasicParams> = {
        background { color { primary } }
        color { lighterGray }
        minWidth { "22vw" }
    }

    val nav: Style<BasicParams> = {
        padding { normal }
    }

    val footer: Style<BasicParams> = {
        height { PwaStyles.headerHeight }
        padding { small }
        borders {
            top {
                width { "1px" }
                color { gray }
            }
        }
    }

    val header: Style<FlexParams> = {
        padding { small }
        alignItems { center }
        justifyContent { spaceBetween }
        color { "rgb(44, 49, 54)"}
        borders {
            bottom {
                width { "1px "}
                style { solid }
                color { lightGray }
            }
        }
    }

    val main: Style<BasicParams> = {
        padding { normal }
        background { color { lightestGray } }
        color { "rgb(44, 49, 54)"}
    }

    val tabs: Style<FlexParams> = {
        borders {
            top {
                width { "1px "}
                style { solid }
                color { gray }
            }
        }
    }
}

@ExperimentalCoroutinesApi
open class PwaComponent() {

    companion object {
        init {
            staticStyle("""
                body {
                    height: 100vh;
                    width: 100vw;
        
                    display: grid;
                    grid-template-areas:
                        "brand header"
                        "sidebar main"
                        "sidebar footer";
                    grid-template-rows: ${PwaStyles.headerHeight} 1fr ${PwaStyles.headerHeight};
                    grid-auto-columns: min-content 1fr;
        
                    padding: 0;
                    margin: 0; 
                }
             """.trimIndent())
        }
    }

    val sidebarStatus = storeOf(false)
    val toggleSidebar = sidebarStatus.handle { !it }

    val openSideBar = staticStyle("open-sidebar", """
      @media (max-width: ${Theme().breakPoints.md}) {
        transform: translateX(0) !important;
      }
    """.trimIndent())

    val showBackdrop = staticStyle("show-backdrop", """
        @media (max-width: ${Theme().breakPoints.md}) {
            left : 0 !important;
            opacity: 1 !important;
        }
    """.trimIndent())

    fun mobileSidebar(topPosition: Property): Style<BasicParams> = {
        zIndex { "5000" }
        width(sm = { "90vw"}, md = { unset })
        css(sm = "transform: translateX(-110vw);", md = "transform: unset;")
        position(sm = {
            fixed { top { topPosition } }
        }, md = {
            relative { top { none } }
        })
        css("""
            will-change: transform;
            transition: 
                transform .6s ease-in,
                visibility .6s linear .6s;        
            """.trimIndent())
        boxShadow(sm = { raised }, md = { none })
    }

    var brand = ComponentProperty<Div.() -> Unit> {}
    var header = ComponentProperty<TextElement.() -> Unit> {}
    var actions = ComponentProperty<RenderContext.() -> Unit> {}
    var sidebarToggle = ComponentProperty<RenderContext.(SimpleHandler<Unit>) -> Unit> { sidebarToggle ->
        clickButton({
            display(md = { none })
        }) {
            variant { ghost }
            icon { fromTheme { menu } }
        } handledBy sidebarToggle
    }
    var nav = ComponentProperty<TextElement.() -> Unit> {}
    var main = ComponentProperty<TextElement.() -> Unit> {}
    var footer = ComponentProperty<TextElement.() -> Unit> {}
    var tabs = ComponentProperty<Div.() -> Unit> {}


}

@ExperimentalCoroutinesApi
fun RenderContext.pwa(styling: BasicParams.() -> Unit = {},
                      store: Store<String>? = null,
                      baseClass: StyleClass? = null,
                      id: String? = null,
                      prefix: String = "pwa",
                      build: PwaComponent.() -> Unit = {}
) {
    val component = PwaComponent().apply(build)

    box({
        display(sm = { block }, md = { none })
        opacity { "0" }
        background { color { "rgba(0,0,0,0.6)" } }
        position {
            fixed {
                top { "0" }
                left { "-110vh" }
            }
        }
        width { "100vw" }
        height { "100vh" }
        zIndex { "4000" }
        css(
            """
            transition: 
                opacity .3s ease-in;        
        """.trimIndent()
        )
    }, prefix = "backdrop") {
        className(component.showBackdrop.whenever(component.sidebarStatus.data).name)
        clicks handledBy component.toggleSidebar
    }

    (::header.styled {
        grid(sm = { area { "header" } }, md = { area { "brand" } })
        component.mobileSidebar("none")()
        height(sm = { PwaStyles.headerHeight }, md = { unset })
    }) {
        className(component.openSideBar.whenever(component.sidebarStatus.data).name)
        flexBox({
            height { PwaStyles.headerHeight }
            PwaStyles.brand()
        }) {
            component.brand.value(this)
        }
    }

    (::header.styled {
        grid { area { "header" } }
    }) {
        flexBox({
            height { PwaStyles.headerHeight }
            PwaStyles.header()
        }) {
            section {
                component.header.value(this)
            }
            section {
                lineUp {
                    items {
                        component.actions.value(this)
                        component.sidebarToggle.value(this, component.toggleSidebar)
                    }
                }
            }
        }
    }

    (::aside.styled {
        grid(sm = { area { "main" } }, md = { area { "sidebar" } })
        component.mobileSidebar(PwaStyles.headerHeight)()
        height(sm = { "calc(100vh - ${PwaStyles.headerHeight})" }, md = { unset })
        PwaStyles.sidebar()
    }) {
        className(component.openSideBar.whenever(component.sidebarStatus.data).name)

        flexBox({
            direction { column }
            alignItems { stretch }
            justifyContent { spaceBetween }
            height { full }
        }) {
            (::section.styled {
                PwaStyles.nav()
            }) {
                component.nav.value(this)
            }
            (::section.styled {
                PwaStyles.footer()
            }) {
                component.footer.value(this)
            }
        }
    }

    (::main.styled {
        grid { area { "main" } }
        overflow { auto }
        PwaStyles.main()
//        height { "calc(100vh - ${PwaStyles.headerHeight})" }
    }) {
        component.main.value(this)


//        flexBox({
//            height { full }
//            width { full }
//            direction { column }
//        }) {
//            (::section.styled {
//                flex { grow { "1" } }
//                overflow { auto }
//                PwaStyles.main()
//            }) {
//                component.main.value(this)
//            }
//            (::section.styled {
//                flex { grow { "0" } }
//                height { PwaStyles.headerHeight }
//            }) {
//                component.tabs.value(this)
//            }
//        }


    }

    flexBox( {
        grid { area { "footer" } }
        PwaStyles.tabs()
        direction { row }
        alignItems { center }
        justifyContent { spaceEvenly }
//        overflow { auto }
//        PwaStyles.main()
//        height { "calc(100vh - ${PwaStyles.headerHeight})" }
    }) {
        component.tabs.value(this)
    }

}