import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Random;

public class NBASearch {
    public static void main(String[] args) throws FileNotFoundException {
        PlayerBST t = new PlayerBST();
        Scanner scan = new Scanner(new FileInputStream(args[0]));
        //adds each PlayerInfo to the binary search tree
        while (scan.hasNext()){
            String line = scan.nextLine();
            String[] fields = line.split(",");
            String playerName = fields[0];
            String playerTeam = fields[1];
            String playerPosition = fields[2];
            String playerAge = fields[3];
            String playerHeight = fields[4];
            String playerHeight2 = fields[5];
            String playerWeight = fields[6];
            String playerCollege = fields[7];
            String playerSalary = fields[8];
            PlayerInfo info = new PlayerInfo(playerName, playerTeam, playerPosition, playerAge, playerHeight, playerHeight2, playerWeight, playerCollege, playerSalary);
            t.insert(info);
        }

        //inital choice. play or exit?
        int playerChoice;
        playerChoice = JOptionPane.showConfirmDialog(null, "Would you like to make a search?", "NBASearch", JOptionPane.YES_NO_OPTION);

        if ((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.NO_OPTION))
        	System.exit(0);
        else {
        	String search = "";
        	String foundInfo = "";
        	PlayerInfo player;
        	//use search string's length to determine whether the program restarts to search another player or not. 
        	while (search.length() == 0) {
        		search = JOptionPane.showInputDialog(null, "Please type the name of the player.", JOptionPane.INFORMATION_MESSAGE); 
        		//if the player is found, then the findPlayer function will not return null
        		if (t.findPlayer(search) != null) {
        			player = t.findPlayer(search.trim());
        			JOptionPane.showMessageDialog(null, "Player: " + player.name + ", Team: " + player.team + ", Position: " + player.position + ", Age: " + player.age + ", Height: " + player.height2 + ", Weight: " + player.weight + ", College " + player.college + ", Salary: " + player.salary);
        			//Ask if they would like to make another search
        			playerChoice = JOptionPane.showConfirmDialog(null, "Would you like to make another search?", "NBASearch", JOptionPane.YES_NO_OPTION);
        			if ((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.NO_OPTION)) {
        				System.exit(0);
        			}
        			//if the player chooses to search again, set the length of string to 0 to satisfy while loop condition.
        			else {
        				search = "";
        			}
        		}

        	}
        }
	}
}

//PlayerInfo contains the main aspects of the players that will be used as hints
 class PlayerInfo {
    String name;
    String team;
    String position;
    String age;
    String height;
    String height2;
    String weight;
    String college;
    String salary;

    //constructor method for PlayerInfo
    public PlayerInfo(String name, String team, String position, String age, String height, String height2, String weight, String college, String salary) {
        this.name = name;
        this.team = team;
        this.position = position;
        this.age = age;
        this.height = height;
        this.height2 = height2;
        this.weight = weight;
        this.college = college;
        this.salary = salary;
    }

}

//Binary search tree class to sort the players alphabetically.
//Contains nodes that have the ability to access a left and right node.
 class PlayerBST{
    class Node {
        public String key;
        public PlayerInfo data;
        public Node left, right;

        Node(String key, PlayerInfo data) {
        this.key = key;
        this.data = data;
        }

        
    }

    
    private Node root;

    //constructor method, creates empty binary tree
    public PlayerBST() {
        root = null;
    }

    public void insert(PlayerInfo data) {
        Node child = new Node(data.name, data);
        //if BST is empty, insert the node
        if(root == null) root = child;
        else {
            Node current = root;
            boolean found = false;
            //traverse the tree iteratively, using alphabetical ordering to compare keys
            while(found != true) {
                String currentName = current.key.toLowerCase();
                String childName = child.key.toLowerCase();
                int comp = currentName.compareTo(childName);
                if (comp < 0) {
                    if (current.left == null) {
                        current.left = child;
                        found = true;
                    } else {
                        current = current.left;
                    }
                } else if (comp > 0) {
                    if (current.right == null) {
                        current.right = child;
                        found = true;
                    } else {
                        current = current.right;
                    }
                } else {
                    found = true;
                }
            }
        }
    }

    //Traverse the tree using alphabetical string comparisons(case insensitive)
    public PlayerInfo findPlayer(String key) {
        Node current = root;
        while (current != null) {
            String currentName = current.key.toLowerCase();
            String childName = key.toLowerCase();
            int comp = currentName.compareTo(childName);

            if (comp < 0) {
                current = current.left;
            } else if (comp > 0) {
                current = current.right;
            } else {
                return current.data;
            }
        }
        return null;
    }


}