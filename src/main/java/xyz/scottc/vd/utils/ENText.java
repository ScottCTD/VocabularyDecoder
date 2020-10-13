package xyz.scottc.vd.utils;

public class ENText {

    //Entry
    public static final String MAIN_INTRODUCTION = "<html>\n" +
            "<body>\n" +
            "<h1>What is &quot;Vocabulary Decoder&quot;?</h1>\n" +
            "<p>As the word &quot;Decoder&quot; implies, vocabulary decoder (VD) is a tool, aiming to help non-native English learner effectively memorize English Vocabularies. ScottCTD created this small tool.</p>\n" +
            "<h2>How to use it?</h2>\n" +
            "<p>Choose a VD mode from the <strong>&quot;VD Mode Selection&quot;</strong>, and then hit <strong>&quot;Confirm&quot;</strong> to continue.</p>\n" +
            "<p>VD has many <strong>internal vocabulary lists</strong>, ranging from TOEFL vocabularies to SAT vocabularies, and more lists will be added in the future.</p>\n" +
            "<p>If you want to create <strong>your customized vocabulary lists</strong>, just go to the <strong>&quot;tools&quot;</strong> menu at the <strong>top</strong> of the Lists Selection windows. </p>\n" +
            "<p><strong>Thanks for using!</strong></p>\n" +
            "<p>Still don&#39;t know how to use? Go to the wiki page to get detailed introduction</p>\n" +
            "<h2>Help Me</h2>\n" +
            "<p>If you really want to help me, please contact me through my email.</p>\n" +
            "<p>&nbsp;</p>\n" +
            "</body>\n" +
            "</html>";
    public static final String MODE_SELECTION = "VD Mode Selection";
    public static final String ORDERED_MODE_NAME = "Ordered Mode";
    public static final String ORDERED_MODE_DESCRIPTION = "Questions will be displayed one by one orderly.\n" +
            "Answers can be checked during the progress.\n" +
            "No time limit.\n";

    //List Selection
    public static final String LIST_SELECTION_INTRODUCTION = "<html>\n" +
            "<body>\n" +
            "    <div>\n" +
            "        <h1>Please choose a VD list to continue!</h1>\n" +
            "        Double click the item in the file tree on the right side.\n" +
            "        <h1>Internal VD Lists</h1>\n" +
            "        Internal VD Lists are prepared by Scott, including common SAT and TOEFL vocabularies\n" +
            "        <h1>External VD Lists</h1>\n" +
            "        You can make <b>you own VD Lists</b> and memorize your own vocabularies!\n" +
            "        <br>\n" +
            "        <br>\n" +
            "        Go to the <b>\"tools\"</b> menu at the top of the windows, and then you can choose the way you want to make your own lists.\n" +
            "        <br>\n" +
            "        <br>\n" +
            "        If everything goes right, you will get a file with extension <b>\".vd\"</b>.\n" +
            "        <br>\n" +
            "        <br>\n" +
            "        Then, you should hit the <b>\"import\"</b> button on the right side to make your list appear in the file tree.\n" +
            "        <br>\n" +
            "        <br>\n" +
            "        If you have any question, please <font color=\"#dc143c\">firstly</font> check the official instruction in the GitHub page.\n" +
            "        <br>\n" +
            "        <br>\n" +
            "        If you get a bug, please submit it as an issue in the GitHub! <b>I really appreciate it! Thanks!</b>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>";
    public static final String LIST_SELECTION = "VD Lists Selection";
    public static final String INTERNAL_VD_LISTS = "Internal VD Lists";
    public static final String EXTERNAL_VD_LISTS = "External VD Lists";

    //Ordered Mode
    public static final String ORDERED_MODE_READY_TEXT = "<html>\n" +
            "<body>\n" +
            "<div align=\"center\">\n" +
            "    <font color=\"#0033FF\">Vocabularies as Questions</font> means you need to type <font color=\"#dc143c\">the meaning of a vocabulary</font>.\n" +
            "    <br>\n" +
            "    <font color=\"#0033FF\">Meanings as Questions</font> means you need to type <font color=\"#dc143c\">the corresponding vocabulary of it's meaning</font>.\n" +
            "    <br>\n" +
            "    Note: You <font color=\"#dc143c\">can</font> check the answer and review!\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
    public static final String ORDERED_MODE_SUSPEND_TEXT = "<html>\n" +
            "<body>\n" +
            "    <div align=\"center\">\n" +
            "        <font color=\"#ff7f50\">SUSPENDED</font>\n" +
            "        <br>" +
            "        Click <font color=\"#dc143c\">HERE</font> to continue!\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>";

    //VDFileConverter01Dialog
    public static final String CONVERTER_01_INFO = "<html>\n" +
            "<body>\n" +
            "    The first way for creating .vd file.\n" +
            "    <br>\n" +
            "    Format: [vocabularies]\\t[meanings]\\n\n" +
            "    <br>\n" +
            "    For more info, go to the GitHub wiki!\n" +
            "</body>\n" +
            "</html>";
}
