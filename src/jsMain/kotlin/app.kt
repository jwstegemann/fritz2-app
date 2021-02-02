import dev.fritz2.binding.RootStore
import dev.fritz2.binding.storeOf
import dev.fritz2.components.clickButton
import dev.fritz2.components.flexBox
import dev.fritz2.components.icon
import dev.fritz2.components.navBar
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.html.render
import dev.fritz2.styling.name
import dev.fritz2.styling.params.styled
import dev.fritz2.styling.staticStyle
import dev.fritz2.styling.theme.Theme
import dev.fritz2.styling.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi

object SidebarStore : RootStore<Boolean>(false) {
    val toggle = handle { !it }
}

val openSideBar = staticStyle("sidebar-open", """
  display: grid;
  grid-template-columns: [nav] 2fr [escape] 1fr;

  @media (max-width: 540px) {
    will-change: transform;
    transition: 
      transform .6s cubic-bezier(0.16, 1, 0.3, 1),
      visibility 0s linear .6s;
        
    position: sticky;
    top: 0;
    max-height: 100vh;
    overflow: hidden auto;
    overscroll-behavior: contain;
//    visibility: visible;
    transform: translateX(0);
  }
""".trimIndent())

@ExperimentalCoroutinesApi
fun initApp() {
    // language=CSS
    staticStyle("""
        body {
            min-width: 100%;
            display: grid;
            grid: [stack] 1fr / min-content [stack] 1fr;
            min-block-size: 100vh;
        }

        @media (max-width: 540px) {
            body > aside, body > main {
                grid-area: stack;
            }

            body > aside {
                transform: translateX(-110vw);
                will-change: transform;
                transition:
                    transform 1.8s cubic-bezier(0.16, 1, 0.3, 1),
                    visibility .8s linear .6s;
//                visibility: hidden;
            }
        }
    """.trimIndent())

}

@ExperimentalCoroutinesApi
fun RenderContext.app() {
    (::aside.styled {
        zIndex { "5000" }
        display { flex }
        direction { column }
        alignItems { flexStart }
        position(sm = {
            fixed { top { "0" } }
        },
        md = {
            sticky { top { "0" } }
        })
    }) {
        className(openSideBar.whenever(SidebarStore.data).name)

        (::header.styled {

            background { color { "red" } }
            flex { grow { "1" } }
            css("""
                min-block-size: 3rem;
                //margin-block-end: 1rem;
            """.trimIndent())
        }) {
            h1 {
                +"Brand"
            }
        }

        (::nav.styled {
            css("min-block-size: 100vh;")
            flex { grow { "1" } }
            paddings {
                top { "6rem" }
                bottom { large }
                left { large }
                right { large }
            }
            background { color { "blue" } }
            display { inlineFlex }
            direction { column }
            fontSize { large }
            color { "white" }
        }) {
            h4 { + "Some Links"}
            a { href("#"); + "aaa" }
            a { href("#"); + "aaa" }
            a { href("#"); + "aaa" }
            a { href("#"); + "aaa" }

            h4 { + "Some more"}
            a { href("#"); + "aaa" }
            a { href("#"); + "aaa" }
            a { href("#"); + "aaa" }
            a { href("#"); + "aaa" }
        }
    }
    (::main.styled {
    }) {
        (::header.styled {
            background { color { "red" } }
            position { sticky { top { "0" } } }
            display { flex }
            alignItems { center }
            justifyContent { spaceBetween }
            css("""
                min-block-size: 3rem;
                margin-block-end: 1rem;
            """.trimIndent())
        }) {

            h1 {
                +"Title"
            }

            clickButton {
                icon { fromTheme { menu } }
            } handledBy SidebarStore.toggle
        }

        article {
            p {
                +"""
               Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.
            """.trimIndent()
            }
            p {
                +"""
               Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.
            """.trimIndent()
            }
            p {
                +"""
               Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.
            """.trimIndent()
            }
        }

    }
}


fun main() {
    initApp()

    render {
        app()
    }
}