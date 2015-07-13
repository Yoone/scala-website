/*
 * Scala website
 * https://github.com/Yoone/scala-website
 */

(function ($) {

    // The goal being to work with Slick, we only store the game id
    // so we need to refresh the data frequently.
    var game_id = 0;
    var full_hangman = 5;
    var current_hangman = 0;

    var base_url = "/hangman/json";

    /*
     * Functions
     */

    function generate_buttons() {
        for (var l = 'A'.charCodeAt(0), end = 'Z'.charCodeAt(0); l <= end; ++l) {
            var letter = String.fromCharCode(l);
            $("#buttons").append('<button data-letter="' + letter + '">' + letter + '</button>');
        }
    }

    function update_hangman() {
        var hm = "";
        switch (++current_hangman) {
            case 1:
                for (var i = 0; i < 5; ++i) {
                    hm += "\n";
                }
                break;
            case 2:
                hm += "   ______   \n";
                hm += "   |/       \n";
                hm += "   |        \n";
                hm += "   |        \n";
                hm += "   |        \n";
                break;
            case 3:
                hm += "   ______   \n";
                hm += "   |/   |   \n";
                hm += "   |    O   \n";
                hm += "   |        \n";
                hm += "   |        \n";
                break;
            case 4:
                hm += "   ______   \n";
                hm += "   |/   |   \n";
                hm += "   |    O   \n";
                hm += "   |   -|-  \n";
                hm += "   |        \n";
                break;
            case 5:
                hm += "   ______   \n";
                hm += "   |/   |   \n";
                hm += "   |    O   \n";
                hm += "   |   -|-  \n";
                hm += "   |   / \\  \n";
                break;
        }
        hm += "   |        \n";
        hm += "___|\\_______\n";
        $("#figure").text(hm);
    }

    /*
     * Callbacks
     */

    function create_game() {
        $("#status").text("Loading...").show();
        $(this).hide();
        $.getJSON(base_url, {action: "new"}, function(data) {
            if (typeof data.error !== "undefined") {
                $("#status").text(data.error);
                console.log("Error: " + data.error);
            }
            else {
                game_id = data.game_id;
                generate_buttons();
                $("#status").text("Ready.");
                $("#word").text(data.word);
            }
        });
    }

    function send_letter() {
        var letter = $(this).attr("data-letter")[0].toUpperCase();

        $("#status").text("Sending letter...");
        $(this).hide();
        $.getJSON(base_url, {action: letter, id: game_id}, function(data) {
            console.log(data);
            if (typeof data.error !== "undefined") {
                $("#status").text(data.error);
                console.log("Error: " + data.error);
            }
            else {
                if (data.success) {
                    $("#word").text(data.word);
                }
                else {
                    update_hangman();
                }
                // Status update
                if (data.finished) {
                    $("#buttons").hide();
                    $("#status").text("Congratulations! Hit F5 for a new game.");
                }
                else if (current_hangman >= full_hangman) {
                    $("#buttons").hide();
                    $("#status").text("Game Over! How embarrassing... Hit F5 for a new game.");
                }
                else {
                    $("#status").text("Ready.");
                }
            }
        });
    }

    /*
     * Events
     */

    $("body").on("click", "[data-create]", create_game);
    $("body").on("click", "[data-letter]", send_letter);

})(jQuery)
