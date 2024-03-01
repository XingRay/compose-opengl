fun stringResource(id: String): String {
    // 在这里根据资源标识符 id 加载对应的字符串
    return when (id) {
        "show_less" -> "Show Less"
        "show_more" -> "Show More"
        else -> ""
    }
}