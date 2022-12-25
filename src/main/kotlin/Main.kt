import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

fun main() {

    LangTools.setSpellTokens(
        arrayListOf(
            "Цирульников",
            "Харичкин",
            "Факторович",
            "трассируемость",
            "трассируемости",
            "Хабра",
        ),
        arrayListOf(
            "ИТ-продукта"
        )
    )

    LangTools.ruleTokenExceptions = mapOf("Latin_letters" to setOf("ЮMoney"))

    val doc = markupDocument {

        title("DocOps на Flow 2022")

        p { + "29-30 ноября прошла конференция для аналитиков FlowConf 2022. Основная особенность конференции -- ее ориентация на конкретные практические рецепты. Одним из направлений, которое содержит много таких рецептов, стал Docs As Code или, в более широком смысле, DocOps в работе аналитика. В этом посте представляю обзор этого направления." }

        p { + "Сергей Гришанов и Евгений Зингер рассказали о том, как в Тинькофф пришли к практике хранения документации в одном репозитории с кодом (Docs as Code). Спикеры работают по разным направлениям. У каждого из этих направлений разные задачи и организация процессов. Тем не менее результат получился одинаковый: в обеих командах значительно улучшилось взаимодействие аналитиков и разработчиков, а документация теперь полностью соответствует текущему состоянию информационного продукта." }

        p { + "Роман Цирульников рассказал о том, как в ЮMoney используется Docs as Code для организации репозитория архитектуры, где этот подход также показал свою эффективность." }

        p { + "Никита Харичкин провел мастер-класс по использованию Sequence диаграмм в PlantUML. Никита давно рассказывает об этой теме, каждый раз находя всё больше и больше возможностей в данном инструменте для решения ежедневных практических задач." }

        p { + "В рамках конференции был проведен круглый стол, в котором собрались технические писатели и аналитики -- Константин Валеев, Николай Волынкин, Лана Новикова, Николай Поташников, Семен Факторович, -- с целью в принципе ответить на вопрос, как DocOps может помочь в работе аналитика." }

        p { + "Был показан пример, показывающий, что вне зависимости от того, как организована работа с документацией в методологии DocOps, на выходе мы можем получить документы в любом формате в соответствии с требованиями заказчика (html, pdf, docx, ...). Т.е. работая в методологии DocOps мы концентрируемся не на выходных форматах, а на содержании процесса документирования." }

        p { + "Также была рассмотрена ключевая для аналитика проблема -- управление требованиями. В дискуссии приняли участие более 80 человек. Были обозначены следующие задачи управления требованиями, традиционно вызывающие проблемы:" }

        fun AdocDSLOList.lps(init: AdocDSLParagraph.() -> Unit): AdocDSLListItem {
            return li { p { ignoreRules("UPPERCASE_SENTENCE_START"); apply(init) } }
        }

        ol {
            arrayOf(
                "управление атомарными требования и поддержка актуальности",
                "трассируемость вплоть до кода",
                "ведение модели",
                "срезы (представления)",
                "связь между текстом и диаграммами",
                "совместная работа",
                "публичное представление",
                "разные выходные форматы документов, формируемые, в том числе, не только из требований",
                "контроль качества",
                "требования должны продолжать жить как документация",
            ).let { list ->
                list.mapIndexed { index, item ->
                    lps { + item + if (index + 1 == list.size) "." else ";" }
                }
            }
        }

        p { + "Часть этих проблем DocOps решает понятным образом, например возможное решение проблемы поддержки актуальности требований и их трассируемости рассмотрено в уже упомянутом докладе Сергея Гришанова и Евгения Зингера. Чаще готовых рецептов нет или они не очевидны." }

        p { + "Были определены технологии, которые потенциально могут обеспечить решение всех указанных проблем." }

        p { + "Были определены технологии, которые потенциально могут обеспечить решение всех указанных проблем." }

        val sphinxNeeds = link("Shinx-needs", "https://github.com/useblocks/sphinx-needs")

        fun AdocDSLOList.lp(init: AdocDSLParagraph.() -> Unit): AdocDSLListItem {
            return li { p(init) }
        }

        ol {
            lp { + sphinxNeeds + " позволяет ввести в документацию термины, свойственные для управления требованиями, и обвязать их метаданными." }
            lp { + link("Gherkin", "https://github.com/cucumber") + " позволяет формулировать тесты на языке, одновременно понятном и заинтересованным лицам, и интерпретируемым внутри программного продукта." }
            lp { + link("Jetbrains MPS", "https://www.jetbrains.com/mps/") + " и аналогичные инструменты, которые позволяют писать собственные языки. Пример языка описаний требований с помощью Jetbrains MPS можно найти " + link("здесь", "http://mbeddr.com/") + "." }
            li {
                p { + "Языки, которые содержат удобные средства для создания внутренних DSL (Kotlin DSL, Haskell, F#, Groovy, Ruby, ...)." }
                p { + "В частности, был рассмотрен пример создания языка документации на Kotlin DSL. Данный язык является оберткой " + link("Writerside", "https://lp.jetbrains.com/writerside/") + ", но позволяет в документацию вводить элементы обычных языков программирования -- циклы, функции и т.д., которые автоматизируют рутинные операции документирования." }
                p { + "Этот же подход можно использовать, как и в случае со " + sphinxNeeds + " для введения в язык собственных элементов, например, для управления структурой требований" }
            }
        }

        p { + "У каждой из указанных технологий есть определенные ограничения. Конечно, хотелось бы иметь универсальные решения или, хотя бы, подходы. Но даже сейчас сам подход DocOps, при котором мы объединяем процессы документирования, разработки и доставки ИТ-продукта в одно целое, позволяет вполне эффективно подбирать технологии для решения конкретной задачи." }

        section { title("Выводы") }

        ol {
            lp { + "Технологии документирования развиваются очень быстро. Еще 10 лет назад все пользовались только MS Word. Сегодня по оценкам участников круглого стола -- в лучшем случае 25%." }
            lp { + "Начать использовать Docs as Code очень просто -- есть очень простые подходы, которые сразу дают результат." }
            lp { + "Возможности DocOps достаточно широки, чтобы эффективно решать практически любые проблемы документирования, стоящие перед аналитиками." }
        }

        p { + "P.S. По горячим следам попробовал сделать " + link("обёртку Kotlin DSL для Asciidoc и эту заметку написать в ней", "https://github.com/fiddlededee/flow-2022-docops") + ". Конечно, писать не настолько удобно, как в Markdown, reStructuredText или Asciidoc. Однако тестировать текст, автоматизировать рутинные операции, создавать собственные элементы языка можно непосредственно внутри проекта, используя привычные инструменты работы с языком Kotlin. Выгрузка в формате Markdown Habr для публикации тоже получилась очень удобной." }

    }
    Files.createDirectories(Paths.get("output"))
    File("output/flow-docops.md").writeText(doc.toHabrMd())
    File("output/flow-docops.adoc").writeText(doc.toString())
    File("output/flow-docops.txt").writeText(doc.toText())
}
