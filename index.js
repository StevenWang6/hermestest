// Hermes Test Project - Main Entry Point
console.log("🚀 Hermes Agent GitHub Test Project");
console.log("===================================");

// Sample function
function greet(name) {
    return `Hello, ${name}! Welcome to Hermes Test Project.`;
}

// Example usage
const userName = "GitHub User";
console.log(greet(userName));

// Simple calculation example
const calculateSum = (a, b) => a + b;
console.log(`Sum of 5 and 7: ${calculateSum(5, 7)}`);

// Export for module usage
module.exports = {
    greet,
    calculateSum
};
