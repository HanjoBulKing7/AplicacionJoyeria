import { MdCategory } from "react-icons/md";
import { MdInventory } from "react-icons/md";
import { RiLayout6Fill } from "react-icons/ri";

export const sidebarContent = [
    {
        name: "Dashboard", href: "/admin", icon: RiLayout6Fill
    },
    {
        name: "Inventory", href: "/admin/inventory", icon: MdInventory
    },
    {
        name: "Categories", href: "/admin/categories", icon: MdCategory
    },
];