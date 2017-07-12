package me.avo.clinical.trials

import java.io.File

object ClassLoader {

    const val dir = "D:\\DL\\clinical-trials\\"
    const val delimiter = "|"

    const val summaryFile = "brief_summaries.txt"
    const val keywordFile = "keywords.txt"

    fun <T : Any> extract(name: String, body: (split: List<String>) -> T): List<T> = File(dir + name).useLines { lines ->
        lines.drop(1).filter { it.isNotBlank() }.map {
            val split = it.split(delimiter)
            body(split)
        }.toList()
    }

    fun loadSummaries() = extract(summaryFile) {
        it[1] to it[2].removeSurrounding("\"").trim()
    }.toMap()

    fun loadKeywords() = loadPair(keywordFile)

    fun loadConditions() = loadPair("browse_conditions.txt")

    fun loadInterventions() = loadPair("browse_interventions.txt")


    fun loadPair(fileName: String) = extract(fileName) { it[1] to it[2].capitalize() }
            .groupBy { it.first }
            .mapValues { it.value.map { it.second } }

}