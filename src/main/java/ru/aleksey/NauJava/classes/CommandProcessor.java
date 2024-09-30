package ru.aleksey.NauJava.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aleksey.NauJava.entities.Product;
import ru.aleksey.NauJava.services.ProductService;
import ru.aleksey.NauJava.services.UserService;

import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class CommandProcessor
{
    private final UserService userService;
    private final ProductService productService;
    private final Random random;

    @Autowired
    public CommandProcessor(UserService userService, ProductService productService)
    {
        this.userService = userService;
        this.productService = productService;
        random = new Random(3142L);
    }

    public void processCommand(String input)
    {
        var cmd = input.split(" ");
        if (cmd.length > 0 && Objects.equals(cmd[0], "user"))
        {
            if (cmd.length == 3 && Objects.equals(cmd[1], "create"))
            {
                var userId = random.nextLong();
                var userUsername = cmd[2];
                userService.createUser(userId, userUsername);
                var text = String.format("Создан пользователь {\"id\": \"%s\", \"username\":\"%s\"}", userId, userUsername);
                System.out.println(text);
            }
            else if (cmd.length == 4 && Objects.equals(cmd[1], "update"))
            {
                var userId = Long.parseLong(cmd[2]);
                var userUsername = cmd[3];
                userService.updateUser(userId, userUsername);
                var text = String.format("Обновлен пользователь {\"id\": \"%s\", \"username\":\"%s\"}", userId, userUsername);
                System.out.println(text);
            }
            else if (cmd.length == 4 && Objects.equals(cmd[1], "add-product"))
            {
                var userId = Long.parseLong(cmd[2]);
                var productId = Long.parseLong(cmd[3]);
                userService.addProductToUser(userId, productId);
                System.out.println("Пользователю добавлен продукт");
            }
            else if (cmd.length == 3 && Objects.equals(cmd[1], "get-products"))
            {
                var userId = Long.parseLong(cmd[2]);
                var products = userService.getProducts(userId);
                System.out.println(products);
            }
            else
            {
                System.out.println("Введена неизвестная команда...");
            }
        }
        else if (cmd.length > 0 && Objects.equals(cmd[0], "product"))
        {
            if (cmd.length == 4 && Objects.equals(cmd[1], "create"))
            {
                var productId = random.nextLong();
                var productName = cmd[2];
                var productCalories = Integer.parseInt(cmd[3]);
                productService.createProduct(productId, productName, productCalories);
                var text = String.format("Создан продукт {\"id\": \"%s\", \"name\":\"%s\", \"calories\":\"%s\"}", productId, productName, productCalories);
                System.out.println(text);
            }
            else if (cmd.length == 5 && Objects.equals(cmd[1], "update"))
            {
                var productId = Long.parseLong(cmd[2]);
                var productName = cmd[3];
                var productCalories = Integer.parseInt(cmd[4]);
                productService.updateProduct(productId, productName, productCalories);
                var text = String.format("Обновлен продукт {\"id\": \"%s\", \"name\":\"%s\", \"calories\":\"%s\"}", productId, productName, productCalories);
                System.out.println(text);
            }
            else if (cmd.length == 2 && Objects.equals(cmd[1], "list"))
            {
                System.out.println(productService.getAllProducts().stream().filter(Objects::nonNull).map(Product::toString).collect(Collectors.joining(", ", "[", "]")));
            }
            else
            {
                System.out.println("Введена неизвестная команда...");
            }
        }
        else
        {
            System.out.println("Введена неизвестная команда...");
        }
    }
}
