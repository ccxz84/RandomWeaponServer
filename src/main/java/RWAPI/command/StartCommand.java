package RWAPI.command;

import java.util.ArrayList;
import java.util.List;

import RWAPI.main;
import RWAPI.Character.PlayerData;
import RWAPI.game.Game;
import RWAPI.game.Game.gameStart;
import RWAPI.util.ClassList;
import RWAPI.util.GameStatus;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class StartCommand implements ICommand {

	@Override
	public int compareTo(ICommand o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		return "StartCommand";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "����";
	}

	@Override
	public List<String> getAliases() {
		List<String> aliase = new ArrayList();
		aliase.add("rwpstart");
		return aliase;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		
		if(main.game.getReadyStatus()) {
			main.game.start = GameStatus.PRESTART;
			server.getPlayerList().sendMessage(new TextComponentString("게임이 시작됩니다."));
			gameStart game = new gameStart(main.game);
			ClassList.setList();
			for(PlayerData player : main.game.player().values()) {
				try {
					game.appointedClass(player);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				player.getPlayer().sendMessage(new TextComponentString("당신의 직업은 '" + player.get_class().ClassName + "' 입니다."));
			}
			
		}
		else {
			server.getPlayerList().sendMessage(new TextComponentString("준비를 하지 않은 플레이어: "));
			for(PlayerData player : main.game.playerReady().values()) {
				server.getPlayerList().sendMessage(new TextComponentString(player.getPlayer().getName()));
			}
		}
	}
	
	
	

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}

}
