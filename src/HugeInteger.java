public class HugeInteger {
    // four state variable names
    private boolean isPositive; // whether the number is positive or negative
    private Node head; // the most significant digit
    private Node tail; // the least significant digit
    private int length; // length of the number
    // Internal class Node for the assignment
    public class Node {
        int data;
        Node prev;
        Node next;
        Node(int data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    // constructor to create the empty linked list
    public HugeInteger() {
        isPositive = true;
        head = tail = null;
        length = 0;
    }

    public HugeInteger(String number) {
        this(); // calls the default constructor
        if (number.startsWith("-")) {
            isPositive = false; // is negative based on the presence of -
            number = number.substring(1);
        }
        number = number.replaceFirst("^0+(?!$)", ""); // regex to remove leading zeros
        // Iterate through the characters of the string
        for (int i = 0; i < number.length(); i++) {
            int digit = number.charAt(i) - '0'; // Convert character to int
            addLast(digit); // ad the digit to the end of the linked list
        }
    }

    // Method to concatenate a single digit to the end of the linked list
    public void concatenateDigit(int digit) {
        Node newNode = new Node(digit);
        if (tail == null) {
            head = tail = newNode; // If the list is empty, set head & tail to the new node
        } else {
            // Else, add the new node after the tail and update it
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        length++;
    }

    // Add a single digit to the beginning
    public void addLast(int digit) {
        Node newNode = new Node(digit);
        if (head == null) {
            head = tail = newNode;
        } else {
            // Add the new node before the head and updates it.
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        length++;
    }


    public HugeInteger addPositive(HugeInteger num2) {
        HugeInteger result = new HugeInteger();
        Node current = this.head;
        Node Num2 = num2.head;
        int carry = 0;

        // Iterate through the digits of both numbers to perform addition
        while (current != null || Num2 != null || carry != 0) {
            int sum = carry;
            if (current != null) {
                sum += current.data;
                current = current.next;
            }
            if (Num2 != null) {
                sum += Num2.data;
                Num2 = Num2.next;
            }
            result.concatenateDigit(sum % 10); // Add the result digit to the result number
            carry = sum / 10; // Update carry for the next iteration
        }

        return result; // Return result of addition
    }

    // Method to compare two HugeInteger numbers
    public int compareTo(HugeInteger num2) {
        // Comparing with positive and negative
        if (this.isPositive && !num2.isPositive)
            return 1;
        if (!this.isPositive && num2.isPositive)
            return -1;
        // Comparing the length of the node
        if (this.length > num2.length)
            return this.isPositive ? 1 : -1;
        if (this.length < num2.length)
            return this.isPositive ? -1 : 1;

        // Comparing digits from most significant to least
        Node current = this.tail;
        Node Num2 = num2.tail;

        while (current != null && Num2 != null) {
            if (current.data > Num2.data)
                return this.isPositive ? 1 : -1;
            if (current.data < Num2.data)
                return this.isPositive ? -1 : 1;
            current = current.prev;
            Num2 = Num2.prev;
        }
        return 0;
    }

    // Convert the HugeInteger to a string representation
    @Override
    public String toString() {
        if (head == null)
            return "0";
        StringBuilder sb = new StringBuilder(isPositive ? "" : "-");
        Node current = tail;
        while (current != null) {
            sb.append(current.data); // Append each digit to the StringBuilder
            current = current.prev; // Move to the next digit
        }
        return sb.toString(); // Return the string representation of the number
    }
}
