package app.src.main.java;

public interface CharacterDisplay {
 void display(char[] charArray, char required);
}

class BasicCharacterArrayDisplay implements CharacterDisplay {
    @Override
    public void display(char[] charArray, char required) {
        System.out.println("   -----");
        System.out.print(" / ");
        int maxIndex = Math.min(charArray.length, 3);
        for (int i = 0; i < maxIndex; i++) {
            System.out.print(charArray[i] + " ");
        }

        System.out.print("\\");
        System.out.println();
        System.out.println("||   " + "\u001B[33m" + required + "\u001B[0m" + "   ||");
        System.out.print(" \\ ");
        maxIndex = Math.min(charArray.length, 6);
        for (int i = 3; i < maxIndex; i++) {
            System.out.print(charArray[i] + " ");
        }

        System.out.println("/");
        System.out.println("   -----");
    }
}
// Decorator
class DecoratedCharacterArrayDisplay implements CharacterDisplay {
    private CharacterDisplay decoratedDisplay;

    public DecoratedCharacterArrayDisplay(CharacterDisplay decoratedDisplay) {
        this.decoratedDisplay = decoratedDisplay;
    }

    @Override
    public void display(char[] charArray, char required) {
        // Add additional decoration logic here if needed
        decoratedDisplay.display(charArray, required);
    }
}
